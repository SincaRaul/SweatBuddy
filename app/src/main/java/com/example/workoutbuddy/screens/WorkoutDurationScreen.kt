package com.example.workoutbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workoutbuddy.api.ApiHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDurationScreen(viewModel: QuestionnaireViewModel, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) } // To control dropdown menu visibility
    var selectedDuration by remember { mutableStateOf(60) } // Default workout duration
    val durations = listOf(30, 45, 60, 75, 90, 105, 120) // Options for durations
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val answers = viewModel.answers

    // Capture the prompt used for potential retries
    val promptState = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 8 of 8",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Workout Length",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Dropdown Menu Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("$selectedDuration Minutes")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFA5D6A7)) // Light green background
                    ) {
                        durations.forEach { duration ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedDuration = duration
                                    viewModel.setWorkoutDuration(duration)
                                    expanded = false
                                },
                                text = {
                                    Text(
                                        text = "$duration Minutes",
                                        color = MaterialTheme.colorScheme.onBackground // Match text color
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Navigation Button
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

                promptState.value = prompt

                // Fetch the workout plan using the shared ViewModel
                viewModel.fetchWorkoutPlan(prompt, ApiHelper)

                navController.navigate("workout_plan_screen")
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(60.dp)
        )
        {

            Text("Generate",
                fontSize = 18.sp)


        }

        // Display error message if any
        errorMessage?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }
}
