package com.example.gulmadetektor.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM detection_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<DetectionHistory>>
    
    @Query("SELECT * FROM detection_history WHERE id = :id")
    suspend fun getHistoryById(id: Int): DetectionHistory?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: DetectionHistory)
    
    @Update
    suspend fun updateHistory(history: DetectionHistory)
    
    @Delete
    suspend fun deleteHistory(history: DetectionHistory)
    
    @Query("DELETE FROM detection_history")
    suspend fun deleteAllHistory()
    
    @Query("SELECT COUNT(*) FROM detection_history")
    suspend fun getHistoryCount(): Int
}
