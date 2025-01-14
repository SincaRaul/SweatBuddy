package com.example.workoutbuddy.screens

import androidx.lifecycle.ViewModel
import com.example.workoutbuddy.models.QuestionnaireAnswers

class QuestionnaireViewModel : ViewModel() {
    val answers = QuestionnaireAnswers()

    fun setName(name: String) {
        answers.name = name
    }
    fun setGender(gender: String) {
        answers.gender = gender
    }

    fun setWorkoutFrequency(frequency: Int) {
        answers.workoutFrequency = frequency;
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

}
