package com.example.workoutbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutFrequencyScreen(viewModel: QuestionnaireViewModel, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) } // To control dropdown menu visibility
    var selectedFrequency by remember { mutableStateOf("Choose a number") } // Default dropdown text

    val trainingFrequencies = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 7 of 8",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sessions Per Week",
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
                        Text(text = selectedFrequency)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFA5D6A7)) // Light green background
                    ) {
                        trainingFrequencies.forEach { frequency ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedFrequency = frequency
                                    viewModel.setWorkoutFrequency(frequency.toInt()) // Set workout frequency
                                    expanded = false
                                },
                                text = {
                                    Text(
                                        text = frequency,
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
                    navController.navigate("workout_duration_screen") // Navigate to the next screen
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                    .height(50.dp) // Increased button height for a "fatter" appearance
            ) {
                Text("Next")
            }
        }
    }

