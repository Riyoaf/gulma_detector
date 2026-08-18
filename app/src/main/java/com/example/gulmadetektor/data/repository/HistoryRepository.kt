package com.example.gulmadetektor.data.repository

import android.content.Context
import android.net.Uri
import com.example.gulmadetektor.data.remote.DetectionHistoryDto
import com.example.gulmadetektor.data.remote.SupabaseModule
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class HistoryRepository(private val context: Context) {
    private val client = SupabaseModule.client
    
    suspend fun getAllHistory(): List<DetectionHistoryDto> {
        client.auth.currentUserOrNull() ?: throw Exception("Anda belum login")
        return client.postgrest["detection_history"]
            .select()
            .decodeList<DetectionHistoryDto>()
            // Sort client-side, or could do .order("created_at", Order.DESCENDING) in select
            .sortedByDescending { it.createdAt }
    }
    
    suspend fun getHistoryById(id: Long): DetectionHistoryDto? {
        return client.postgrest["detection_history"]
            .select { filter { eq("id", id) } }
            .decodeSingleOrNull<DetectionHistoryDto>()
    }
    
    suspend fun insertHistory(history: DetectionHistoryDto, localImageUri: String? = null) {
        val user = client.auth.currentUserOrNull() ?: throw Exception("Anda belum login")
        
        var imageUrl = history.imageUrl
        
        // Upload image if it's a local URI (not starting with http)
        if (localImageUri != null && !localImageUri.startsWith("http")) {
            val fileName = "user_${user.id}_${UUID.randomUUID()}.jpg"
            val bucket = client.storage.from("detection_images")
            
            withContext(Dispatchers.IO) {
                val inputStream = context.contentResolver.openInputStream(Uri.parse(localImageUri))
                if (inputStream != null) {
                    val bytes = inputStream.readBytes()
                    bucket.upload(fileName, bytes)
                    imageUrl = bucket.publicUrl(fileName)
                } else {
                    throw Exception("Gagal membaca file gambar")
                }
            }
        }
        
        val historyToInsert = history.copy(
            userId = user.id,
            imageUrl = imageUrl
        )
        
        client.postgrest["detection_history"].insert(historyToInsert)
    }
    
    suspend fun updateHistory(history: DetectionHistoryDto) {
        if (history.id == null) throw Exception("Cannot update history without ID")
        client.postgrest["detection_history"]
            .update({
                set("notes", history.notes)
            }) {
                filter { eq("id", history.id) }
            }
    }
    
    suspend fun deleteHistory(history: DetectionHistoryDto) {
        if (history.id == null) return
        client.postgrest["detection_history"]
            .delete {
                filter { eq("id", history.id) }
            }
    }
    
    suspend fun deleteAllHistory() {
        val user = client.auth.currentUserOrNull() ?: return
        client.postgrest["detection_history"]
            .delete {
                filter { eq("user_id", user.id) }
            }
    }
    
    suspend fun deleteHistoryByIds(ids: List<Long>) {
        if (ids.isEmpty()) return
        client.postgrest["detection_history"]
            .delete {
                filter { isIn("id", ids) }
            }
    }
}
