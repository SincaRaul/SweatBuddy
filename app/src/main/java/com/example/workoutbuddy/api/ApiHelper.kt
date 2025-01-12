package com.example.workoutbuddy.api

import android.util.Log
import com.example.workoutbuddy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }

    // Example test API call
    suspend fun testApiCall() {
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.generateWorkoutPlan(
                    ChatCompletionRequest(
                        model = "gpt-3.5-turbo",
                        messages = listOf(
                            ChatMessage(role = "user", content = "Generate a workout plan for a beginner.")
                        ),
                        max_tokens = 150
                    )
                ).execute()
            }
            if (response.isSuccessful) {
                val body = response.body()
                // e.g. print the content of the first choice
                val text = body?.choices?.firstOrNull()?.message?.content ?: "No text found"
                Log.d("API_SUCCESS", text)
            } else {
                Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Log.e("API_EXCEPTION", e.message ?: "Unknown exception")
        }
    }
}
