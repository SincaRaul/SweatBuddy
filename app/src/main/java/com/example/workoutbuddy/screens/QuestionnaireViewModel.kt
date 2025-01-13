package com.example.workoutbuddy.screens

import androidx.lifecycle.ViewModel
import com.example.workoutbuddy.models.QuestionnaireAnswers

class QuestionnaireViewModel : ViewModel() {
    val answers = QuestionnaireAnswers()

    // Possibly define helper methods to update fields:
    fun setName(name: String) {
        answers.name = name
    }
    fun setGender(gender: String) {
        answers.gender = gender
    }
    // Add methods to update answers if needed
}
