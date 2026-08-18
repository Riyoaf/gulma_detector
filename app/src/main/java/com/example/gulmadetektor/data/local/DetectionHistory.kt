package com.example.gulmadetektor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_history")
data class DetectionHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weedName: String,
    val confidence: String,
    val imageUri: String,
    val timestamp: Long = System.currentTimeMillis(),
    val location: String? = null,
    val notes: String? = null
)
