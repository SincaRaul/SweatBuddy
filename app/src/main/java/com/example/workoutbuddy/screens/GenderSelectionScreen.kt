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

@Composable
fun GenderSelectionScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top text
        Text(
            text = "Step 2 of 5",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Push the middle content downward
        Spacer(modifier = Modifier.weight(1f))

        // Middle content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Your Gender",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Button(
                onClick = {
                    navController.navigate("age_screen")
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text("Male")
            }

            Button(
                onClick = {
                    navController.navigate("age_screen")
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text("Female")
            }
        }

        // Push the middle content upward from the bottom
        Spacer(modifier = Modifier.weight(1f))
    }
}