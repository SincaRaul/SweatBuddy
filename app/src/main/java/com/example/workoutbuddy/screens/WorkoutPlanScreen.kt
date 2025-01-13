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
    viewModel: QuestionnaireViewModel, navController: NavHostController,
    fitnessGoal: String,
    workoutFrequency: Int,
    workoutDuration: Int
) {
    // We'll store the plan that comes back from the API
    var workoutPlan by remember { mutableStateOf<WorkoutPlan?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Generate Workout Plan", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // On button click, call GPT
            coroutineScope.launch {
                val prompt = """
                    Additional details:
                    - The user’s main goal is: $fitnessGoal
                    - Focus on a rep and set range per exercise appropriate for $fitnessGoal.
                    - Each workout day can have up to as many exercises you see fit given $workoutDuration minutes allocated time per workout.
                    - Cover all major muscle groups across the week. Split them as you'd think given $workoutFrequency and $workoutDuration and $fitnessGoal.
                    - Provide recommended rest time between sets appropriate for $fitnessGoal.
                    - Summarize any recommended progression or tips for the user.

                    Output the plan in a structured format, listing day-by-day with exercises, sets, and reps.
                    Return a JSON with a 'days' list. Each 'day' has a 'day_name' plus 'exercises' with { "name", "sets", "reps" }.
                    You choose the exercises, day names, structure as you see fit. Make sure it's valid JSON only!
                """.trimIndent()

                val plan = ApiHelper.fetchWorkoutPlan(prompt)
                // update state
                workoutPlan = plan
            }
        }) {
            Text("Fetch Plan from GPT")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // If we have a plan, show the days
        workoutPlan?.let { plan ->
            Text(text = "Your ${plan.days.size}-Day Plan:", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(plan.days) { dayItem ->
                    DayRow(dayItem) {
                        // when a user clicks a row, we can navigate to a detail screen
                        // pass dayItem as an argument (via a shared ViewModel or nav argument)
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedDay", dayItem) // or store an ID

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
