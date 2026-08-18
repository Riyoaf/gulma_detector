package com.example.gulmadetektor.data.remote

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Response Data Classes
data class WeedResponse(
    val status: String,
    val original_url: String,
    val mask_url: String,
    val width: Int,
    val height: Int
)

data class WeedType(
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val image_url: String
)

data class Article(
    val id: Int,
    val title: String,
    val author: String,
    val content: String,
    val source: String,
    val image_url: String
)

interface WeedApi {
    @Multipart
    @POST("/")
    suspend fun predict(@Part image: MultipartBody.Part): WeedResponse

    @retrofit2.http.GET("https://riyoaf--weed-type-api-fastapi-app.modal.run/weeds")
    suspend fun getWeeds(): List<WeedType>

    @retrofit2.http.GET("https://riyoaf--weed-type-api-fastapi-app.modal.run/weeds/{id}")
    suspend fun getWeedById(@retrofit2.http.Path("id") id: Int): WeedType
    
    @retrofit2.http.GET("https://riyoaf--weed-type-api-fastapi-app.modal.run/articles")
    suspend fun getArticles(): List<Article>
    
    @retrofit2.http.GET("https://riyoaf--weed-type-api-fastapi-app.modal.run/articles/{id}")
    suspend fun getArticleById(@retrofit2.http.Path("id") id: Int): Article
}
