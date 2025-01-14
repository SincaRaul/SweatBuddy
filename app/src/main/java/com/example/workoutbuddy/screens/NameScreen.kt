package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.workoutbuddy.api.ApiHelper
import com.example.workoutbuddy.models.WorkoutPlan
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameScreen(viewModel: QuestionnaireViewModel, navController: NavHostController) {
    var name by remember { mutableStateOf(viewModel.answers.name) }
    var workoutPlan by remember { mutableStateOf<WorkoutPlan?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Column {
        TextField(
            value = name,
            onValueChange = {
                name = it
                viewModel.answers.name = it
            }
        )

        Button(onClick = {
            // Navigate to next screen
            navController.navigate("gender_screen")
        }) {
            Text("Next Step")
        }


    }
}

