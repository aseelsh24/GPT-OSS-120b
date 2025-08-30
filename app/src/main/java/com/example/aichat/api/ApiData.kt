package com.example.aichat.api

// The request object for the Hugging Face API
data class ApiRequest(
    val inputs: String
)

// The response object from the Hugging Face API.
// The API typically returns a list containing one or more results.
data class ApiResponse(
    val generated_text: String
)
