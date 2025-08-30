package com.example.aichat.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // The base URL for the Hugging Face Inference API
    private const val BASE_URL = "https://api-inference.huggingface.co/"

    // Configure a client with longer timeouts for potentially long-running AI models
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Lazily create the Retrofit instance
    val instance: HuggingFaceApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(HuggingFaceApiService::class.java)
    }
}
