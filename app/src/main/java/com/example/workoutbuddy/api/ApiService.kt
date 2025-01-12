package com.example.workoutbuddy.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Example request structure for /v1/chat/completions
data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val max_tokens: Int
)

// The API’s response shape differs from the older Completions as well
data class Choice(val message: ChatMessage?)
data class ChatCompletionResponse(val choices: List<Choice>)

interface ApiService {

    // Removed the extra "v1/" so it doesn't become /v1/v1/
    @POST("chat/completions")
    fun generateWorkoutPlan(@Body request: ChatCompletionRequest): Call<ChatCompletionResponse>
}
