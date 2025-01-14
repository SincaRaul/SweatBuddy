// WorkoutPlanScreen.kt
package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.viewmodels.WorkoutPlanViewModel




@Composable
fun  WorkoutPlanScreen(
    viewModel: QuestionnaireViewModel,
    navController: NavHostController,
    workoutPlanViewModel: WorkoutPlanViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val workoutPlan by workoutPlanViewModel.workoutPlan.collectAsState()
    val isLoading by workoutPlanViewModel.isLoading.collectAsState()
    val errorMessage by workoutPlanViewModel.errorMessage.collectAsState()

    val answers = viewModel.answers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Final Workout Plan",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Construct the prompt using the shared ViewModel's answers
                val prompt = """
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
                 
        
                    Output the plan in a structured format, listing day-by-day with exercises, sets, and reps.
                    Return a JSON with a 'days' list. Each 'day' has a 'day_name' plus 'exercises' with { "name", "sets", "reps" }.
                    The day name must be a descriptive name like "Leg Day" or "Chest Day" or for example if .${answers.workoutFrequency} is 2 
                    and you choose to give him an UPPER BODY and LOWER BODY split, you can name the days "Upper Body" and "Lower Body".
                    You could also give him any other workout split you see fit for covering all major muscle groups in the ${answers.workoutFrequency} workouts/week.
                    You choose the exercises, structure as you see fit. Make sure it's valid JSON only!
                """.trimIndent()

                // Fetch the workout plan using the shared ViewModel
                workoutPlanViewModel.fetchWorkoutPlan(prompt, ApiHelper)
                //navController.navigate("day_detail_screen")

            },
            enabled = !isLoading, // Disable button when loading
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fetching...")
            } else {
                Text("Fetch Plan from GPT")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display error message if any
        errorMessage?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Show the plan in a LazyColumn
        workoutPlan?.let { plan ->
            Text(
                text = "Your ${answers.workoutFrequency}-Day Plan:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(plan.days) { dayItem ->
                    DayRow(dayPlan = dayItem) {
                        // Set the selected day in the shared ViewModel
                        workoutPlanViewModel.selectDay(dayItem)
                        // Navigate to DayDetailScreen
                        //navController.navigate("day_detail_screen")
                    }
                }
            }
        }
    }
}
