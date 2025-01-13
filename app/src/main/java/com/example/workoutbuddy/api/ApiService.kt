package com.example.workoutbuddy.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("chat/completions")
    fun generateChatCompletion( // sau generateWorkoutPlan
        @Body request: ChatCompletionRequest
    ): Call<ChatCompletionResponse>
}
