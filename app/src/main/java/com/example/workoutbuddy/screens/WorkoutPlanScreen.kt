package com.example.workoutbuddy.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.models.DayPlan
import com.example.workoutbuddy.models.WorkoutPlan
import kotlinx.coroutines.launch

@Composable
fun WorkoutPlanScreen(
    viewModel: QuestionnaireViewModel,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    var workoutPlan by remember { mutableStateOf<WorkoutPlan?>(null) }

    val answers = viewModel.answers
    // we can read all fields from answers
    // e.g. answers.fitnessGoal, answers.workoutFrequency, answers.workoutDuration, etc.

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        Text(text = "Your Final Workout Plan", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                // Build a prompt using the entire answers object
                val prompt = """
                    Additional details:
                    - The user’s name is ${answers.name}
                    - The user’s main goal is: ${answers.fitnessGoal}
                    - The user’s gender is: ${answers.gender}
                    - The user’s age is: ${answers.age}
                    - The user’s height is: ${answers.height} cm
                    - The user’s weight is: ${answers.weight} kg
                    
                    - Focus on a rep and set range per exercise appropriate for ${answers.fitnessGoal}.
                    - Each workout day can have up to as many exercises you see fit, given ${answers.workoutDuration} minutes per session.
                    - Cover all major muscle groups across the week. The user can train ${answers.workoutFrequency} days/week.
                    - Provide recommended rest time between sets appropriate for ${answers.fitnessGoal}.
                    - Summarize any recommended progression or tips for the user.

                    Output the plan in a structured format, listing day-by-day with exercises, sets, and reps.
                    Return a JSON with a 'days' list. Each 'day' has a 'day_name' plus 'exercises' with { "name", "sets", "reps" }.
                    You choose the exercises, day names, structure as you see fit. Make sure it's valid JSON only!
                """.trimIndent()

                // Now call the API
                val plan = ApiHelper.fetchWorkoutPlan(prompt)

                // This fetchWorkoutPlan can return a WorkoutPlan? or a string.
                // Adjust accordingly:
                workoutPlan = plan  // if plan is a WorkoutPlan?
            }
        }) {
            Text("Fetch Plan from GPT")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Show the plan in a LazyColumn
        if (workoutPlan != null) {
            Text(
                text = "Your ${workoutPlan!!.days.size}-Day Plan:",
                style = MaterialTheme.typography.titleMedium
            )

            LazyColumn {
                items(workoutPlan!!.days) { dayItem ->
                    DayRow(dayItem) {
                        // On day clicked
                        navController.navigate("day_detail_screen")
                    }
                }
            }
        }
    }
}

@Composable
fun DayRow(dayPlan: DayPlan, onDayClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onDayClicked() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = dayPlan.day_name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}
