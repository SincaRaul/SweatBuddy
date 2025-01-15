// WorkoutPlanScreen.kt
package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.models.QuestionnaireAnswers

@OptIn(ExperimentalMaterial3Api::class) // Opt-in for Material3
@Composable
fun WorkoutPlanScreen(
    viewModel: QuestionnaireViewModel,
    navController: NavHostController
) {

    val workoutPlan by viewModel.workoutPlan.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val answers = viewModel.answers

    // Capture the prompt used for potential retries
    val promptState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "UNLEASH YOUR INNER BEAST!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Dynamic Button
        when {
            isLoading -> {
                Button(
                    onClick = { /* Do nothing as it's loading */ },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calculating...")
                }
            }
            workoutPlan != null -> {
                Button(
                    onClick = {
                        navController.navigate("workout_details_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                ) {
                    Text(
                        "Unleash Your Workout Plan!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            errorMessage != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            // Retry fetching the workout plan
                            val prompt = generatePrompt(answers)
                            viewModel.fetchWorkoutPlan(prompt, ApiHelper)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                    ) {
                        Text(
                            "MORE POWER!!!!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "You are too special, please give me more power!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            else -> {
                Button(
                    onClick = {
                        // Construct the prompt using the shared ViewModel's answers
                        val prompt = generatePrompt(answers)
                        promptState.value = prompt

                        // Fetch the workout plan using the shared ViewModel
                        viewModel.fetchWorkoutPlan(prompt, ApiHelper)

                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        "MORE POWER!!!!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}

/**
 * Helper function to generate the prompt string based on answers.
 */
private fun generatePrompt(answers: QuestionnaireAnswers): String {
    return """
        Additional details:
        - The user’s name is ${answers.name}
        - The user’s main goal is: ${answers.fitnessGoal}
        - The user’s gender is: ${answers.gender}
        - The user’s age is: ${answers.age}
        - The user’s height is: ${answers.height} CM
        - The user’s weight is: ${answers.weight} KG
        
        - Focus on a rep and set range per exercise appropriate for ${answers.fitnessGoal}.
        - Each workout day can have up to as many exercises you see fit, given the time frame of ${answers.workoutDuration} minutes per session.
        - Cover all major muscle groups across the week. The user will train for ${answers.workoutFrequency} days/week.
        - DO NOT GIVE A REST DAY AS AN ANSWER. The user will rest on their own.
      
        Output the plan in a structured format, listing day-by-day with exercises, sets, and reps.
        Return a JSON with a 'days' list. Each 'day' has a 'day_name' plus 'exercises' with { "name", "sets", "reps" }.
        The day name must be a descriptive name like "Leg Day" or "Chest Day" or for example if ${answers.workoutFrequency} is 2 
        and you choose to give him an UPPER BODY and LOWER BODY split, you can name the days "Upper Body" and "Lower Body".
        You could also give him any other workout split you see fit for covering all major muscle groups in the ${answers.workoutFrequency} workouts/week.
        You choose the exercises, structure as you see fit. Make sure it's valid JSON only!
    """.trimIndent()
}
