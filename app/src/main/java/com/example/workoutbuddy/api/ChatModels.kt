// ChatModels.kt
package com.example.workoutbuddy.api

/**
 * Data classes used for OpenAI's /v1/chat/completions
 */
data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val max_tokens: Int
)

/**
 * The API response shape from /v1/chat/completions
 */
data class Choice(
    val message: ChatMessage
)

data class ChatCompletionResponse(
    val choices: List<Choice>
)
