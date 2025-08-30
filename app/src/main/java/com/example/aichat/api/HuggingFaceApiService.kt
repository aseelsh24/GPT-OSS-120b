package com.example.aichat.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface HuggingFaceApiService {
    @POST
    suspend fun getCompletion(
        @Url url: String, // Using @Url to allow for dynamic model endpoints
        @Header("Authorization") token: String,
        @Body request: ApiRequest
    ): Response<List<ApiResponse>> // The API returns a list of results
}
