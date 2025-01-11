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
fun AgeScreen(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) } // To control dropdown menu visibility
    var selectedAge by remember { mutableStateOf(18) } // Default age set to 18

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 3 of 5",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "How Young Are You?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown Menu Section
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Age: $selectedAge")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFA5D6A7)) // Light green background like NameScreen
                ) {
                    (12..100).forEach { age ->
                        DropdownMenuItem(
                            onClick = {
                                selectedAge = age
                                expanded = false
                            },
                            text = {
                                Text(
                                    text = age.toString(),
                                    color = MaterialTheme.colorScheme.onBackground // Match text color to background
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
                navController.navigate("height_screen")
            },
            modifier = Modifier
                .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                .height(50.dp) // Increased button height for a "fatter" appearance
        ) {
            Text("Next Step")
        }
    }
}
