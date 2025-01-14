// WorkoutPlan.kt
package com.example.workoutbuddy.models

import java.io.Serializable

data class WorkoutPlan(
    val days: List<DayPlan>
) : Serializable

data class DayPlan(
    val day_name: String,
    val exercises: List<Exercise>
) : Serializable

data class Exercise(
    val name: String,
    val sets: Int,
    val reps: String // Changed from Int to String to handle both numeric and textual reps
) : Serializable
