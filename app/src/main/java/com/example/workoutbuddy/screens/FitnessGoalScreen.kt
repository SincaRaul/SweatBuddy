package com.example.workoutbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessGoalScreen(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) } // To control dropdown menu visibility
    var selectedGoal by remember { mutableStateOf("Please Select Goal") } // Default dropdown text

    val fitnessGoals = listOf(
        "Lose Weight",
        "Build Muscle",
        "Improve Endurance",
        "Increase Flexibility",
        "Improve General Health"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 5 of 5",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "What Is Your Goal?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Dropdown Menu Section
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = selectedGoal)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFA5D6A7)) // Light green background
                ) {
                    fitnessGoals.forEach { goal ->
                        DropdownMenuItem(
                            onClick = {
                                selectedGoal = goal
                                expanded = false
                            },
                            text = {
                                Text(
                                    text = goal,
                                    color = MaterialTheme.colorScheme.onBackground // Match text color
                                )
                            }
                        )
                    }
                }
            }
        }

        // Navigation Button
        Button(
            onClick = {
                navController.navigate("workout_frequency_screen") // Navigate to the next screen
            },
            modifier = Modifier
                .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                .height(50.dp) // Increased button height for a "fatter" appearance
        ) {
            Text("Finish")
        }
    }
}
