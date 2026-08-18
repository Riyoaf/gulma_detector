package com.example.gulmadetektor.data.repository

import com.example.gulmadetektor.data.remote.WeedApi
import com.example.gulmadetektor.data.remote.WeedResponse
import com.example.gulmadetektor.data.remote.WeedType
import com.example.gulmadetektor.data.remote.Article
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class WeedRepository {

    private val api: WeedApi

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
            
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sultanazizul--gulma-segmentation-api-predict-endpoint.modal.run")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            
        api = retrofit.create(WeedApi::class.java)
    }

    suspend fun predict(imageFile: File): WeedResponse {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        return api.predict(body)
    }

    suspend fun getWeeds(): List<WeedType> {
        return api.getWeeds()
    }

    suspend fun getWeedById(id: Int): WeedType {
        return api.getWeedById(id)
    }
    
    suspend fun getArticles(): List<Article> {
        return api.getArticles()
    }
    
    suspend fun getArticleById(id: Int): Article {
        return api.getArticleById(id)
    }
}
