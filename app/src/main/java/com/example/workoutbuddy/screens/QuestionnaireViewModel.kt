package com.example.workoutbuddy.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.models.QuestionnaireAnswers
import com.example.workoutbuddy.models.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionnaireViewModel : ViewModel() {
    val answers = QuestionnaireAnswers()

    // State for Workout Plan
    private val _workoutPlan = MutableStateFlow<WorkoutPlan?>(null)
    val workoutPlan: StateFlow<WorkoutPlan?> = _workoutPlan

    // Loading State
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error Message State
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Last Prompt for Retry
    private var lastPrompt: String = ""

    // Functions to set questionnaire answers

    fun setGender(gender: String) {
        answers.gender = gender
    }

    fun setWorkoutFrequency(frequency: Int) {
        answers.workoutFrequency = frequency
    }

    fun setWeight(weight: Int) {
        answers.weight = weight
    }

    fun setHeight(height: Int) {
        answers.height = height
    }

    fun setAge(age: Int) {
        answers.age = age
    }

    fun setWorkoutDuration(duration: Int) {
        answers.workoutDuration = duration
    }

    fun setFitnessGoal(goal: String) {
        answers.fitnessGoal = goal
    }

    // Function to fetch Workout Plan
    fun fetchWorkoutPlan(prompt: String, apiHelper: ApiHelper) {
        lastPrompt = prompt
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val plan = apiHelper.fetchWorkoutPlan(prompt)
                if (plan != null) {
                    _workoutPlan.value = plan
                } else {
                    _errorMessage.value = "You are too special, please give me more power!"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to retry fetching the workout plan
    fun retryFetch(apiHelper: ApiHelper) {
        if (lastPrompt.isNotEmpty()) {
            fetchWorkoutPlan(lastPrompt, apiHelper)
        }
    }
}
