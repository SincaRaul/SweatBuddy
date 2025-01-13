package com.example.workoutbuddy.models

// This is the top-level JSON object we expect from GPT
data class WorkoutPlan(
    val days: List<DayPlan>
)

// Each "day" in "days"
data class DayPlan(
    val day_name: String,
    val exercises: List<Exercise>
)

// Each exercise in that day
data class Exercise(
    val name: String,
    val sets: Int,
    val reps: Int
)
