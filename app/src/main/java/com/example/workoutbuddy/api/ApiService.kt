package com.example.workoutbuddy.api
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun generateChatCompletion(@Body request: ChatCompletionRequest): Response<ChatCompletionResponse>
}