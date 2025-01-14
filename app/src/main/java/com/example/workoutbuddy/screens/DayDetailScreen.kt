// DayDetailScreen.kt
package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.workoutbuddy.viewmodels.WorkoutPlanViewModel
import com.example.workoutbuddy.models.Exercise

@Composable
fun DayDetailScreen(
    viewModel: QuestionnaireViewModel,
    navController: NavHostController,
    workoutPlanViewModel: WorkoutPlanViewModel = viewModel()
) {
    val selectedDayState = workoutPlanViewModel.selectedDay.collectAsState()
    val selectedDay = selectedDayState.value

    if (selectedDay != null) {
        // When selectedDay is not null, display the day details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = selectedDay.day_name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(selectedDay.exercises) { exercise ->
                    ExerciseItem(exercise)
                }
            }
        }
    } else {
        // When selectedDay is null, prompt the user to go back
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("No DayPlan found. Please go back and select a day.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}

