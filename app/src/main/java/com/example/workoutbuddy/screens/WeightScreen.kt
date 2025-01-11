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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(navController: NavHostController) {
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 5 of 8",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Please enter your weight.",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Input Field Section
            TextField(
                value = weight,
                onValueChange = { weight = it },
                placeholder = { Text("Enter your weight") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFA5D6A7), // Light green background
                    focusedIndicatorColor = Color.Transparent, // Text color when focused
                    unfocusedIndicatorColor = Color.Transparent, // Text color when unfocused
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                singleLine = true
            )
        }

        // Navigation Button
        Button(
            onClick = {
                //if (height.isNotEmpty()) { UNCOMMENT THIS LINE
                    navController.navigate("fitness_goal_screen")
                //} UNCOMMENT THIS LINE
            },
            modifier = Modifier
                .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                .height(50.dp) // Increased button height for a "fatter" appearance
                .offset(y = (-96).dp) // Move the button up by 96dp
        ) {
            Text("Next Step")
        }
    }
}