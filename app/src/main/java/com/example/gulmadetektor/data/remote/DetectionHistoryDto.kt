package com.example.gulmadetektor.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetectionHistoryDto(
    @SerialName("id")
    val id: Long? = null, // Optional for insert
    @SerialName("user_id")
    val userId: String? = null, // Can be set by RLS or manually
    @SerialName("weed_name")
    val weedName: String,
    @SerialName("confidence")
    val confidence: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("notes")
    val notes: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)
