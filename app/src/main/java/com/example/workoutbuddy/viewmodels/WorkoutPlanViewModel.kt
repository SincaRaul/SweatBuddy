// WorkoutPlanViewModel.kt
package com.example.workoutbuddy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.models.DayPlan
import com.example.workoutbuddy.models.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutPlanViewModel : ViewModel() {
    private val _workoutPlan = MutableStateFlow<WorkoutPlan?>(null)
    val workoutPlan: StateFlow<WorkoutPlan?> = _workoutPlan

    private val _selectedDay = MutableStateFlow<DayPlan?>(null)
    val selectedDay: StateFlow<DayPlan?> = _selectedDay

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchWorkoutPlan(prompt: String, apiHelper: ApiHelper) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val plan = apiHelper.fetchWorkoutPlan(prompt)
                if (plan != null) {
                    _workoutPlan.value = plan
                } else {
                    _errorMessage.value = "Failed to fetch workout plan. Please try again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectDay(dayPlan: DayPlan) {
        _selectedDay.value = dayPlan
    }

    fun clearSelectedDay() {
        _selectedDay.value = null
    }
}
