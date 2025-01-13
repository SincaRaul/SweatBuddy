package com.example.workoutbuddy.api

import android.util.Log
import com.example.workoutbuddy.BuildConfig
import com.example.workoutbuddy.models.WorkoutPlan
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // We'll use the same API service we defined
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
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        ChatMessage(role = "user", content = prompt)
                    ),
                    max_tokens = 800 // or however many you want
                )

                // 2) Make the API call (synchronously)
                val response = apiService.generateChatCompletion(requestBody).execute()

                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    // We'll assume the first choice holds the JSON
                    val content = chatResponse?.choices?.firstOrNull()?.message?.content
                    if (content != null) {
                        // 3) Parse JSON into WorkoutPlan data class
                        return@withContext parseJsonToWorkoutPlan(content)
                    } else {
                        Log.e("API_ERROR", "No content in GPT response")
                        null
                    }
                } else {
                    Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
                    null
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message ?: "Unknown exception")
                null
            }
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
