package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workoutbuddy.models.DayPlan
import com.example.workoutbuddy.models.Exercise

@Composable
fun DayDetailScreen(navController: NavController) {
    // Retrieve the selected day from savedStateHandle
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val selectedDay = savedStateHandle?.get<DayPlan>("selectedDay")

    if (selectedDay == null) {
        Text("No DayPlan found. Go Back.")
        return
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${exercise.sets} sets x ${exercise.reps} reps")
        }
    }
}
