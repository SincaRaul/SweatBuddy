package com.example.workoutbuddy.api

import android.util.Log
import com.example.workoutbuddy.BuildConfig
import com.example.workoutbuddy.models.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import java.util.concurrent.TimeUnit

object ApiHelper {

    private const val BASE_URL = "https://api.openai.com/v1/"

    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS) // Increased timeout
            .readTimeout(60, TimeUnit.SECONDS)    // Increased timeout
            .writeTimeout(60, TimeUnit.SECONDS)   // Increased timeout
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Define the API service
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    /**
     * This function calls GPT to produce a structured JSON workout plan.
     *
     * @param prompt is the final prompt string telling GPT exactly how to format JSON
     */
    suspend fun fetchWorkoutPlan(prompt: String): WorkoutPlan? {
        return withContext(Dispatchers.IO) {
            try {
                // 1) Construct the ChatCompletionRequest
                val requestBody = ChatCompletionRequest(
                    model = "gpt-4o-mini",
                    messages = listOf(
                        ChatMessage(role = "user", content = prompt)
                    ),
                    max_tokens = 2500
                )

                // 2) Make the API call
                val response: Response<ChatCompletionResponse> = apiService.generateChatCompletion(requestBody)

                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    // We'll assume the first choice holds the JSON
                    val content = chatResponse?.choices?.firstOrNull()?.message?.content
                    if (content != null) {
                        // Extract JSON from the response
                        val jsonContent = extractJson(content)
                        // 3) Parse JSON into WorkoutPlan data class
                        return@withContext parseJsonToWorkoutPlan(jsonContent)
                    } else {
                        Log.e("API_ERROR", "No content in GPT response")
                        return@withContext null
                    }
                } else {
                    Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message ?: "Unknown exception")
                return@withContext null
            }
        }
    }

    /**
     * This helper extracts the JSON part from the GPT response.
     */
    private fun extractJson(content: String): String {
        val startIndex = content.indexOf("{")
        val endIndex = content.lastIndexOf("}")
        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            content.substring(startIndex, endIndex + 1)
        } else {
            content
        }
    }

    /**
     * This helper uses Gson to parse the JSON string
     * the user typed, into our WorkoutPlan data class.
     */
    private fun parseJsonToWorkoutPlan(jsonString: String): WorkoutPlan? {
        return try {
            val gson = Gson()
            gson.fromJson(jsonString, WorkoutPlan::class.java)
        } catch (e: Exception) {
            Log.e("JSON_PARSE", "Error parsing JSON: ${e.message}")
            null
        }
    }
}
