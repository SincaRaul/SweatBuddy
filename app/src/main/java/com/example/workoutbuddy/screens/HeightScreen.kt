// HeightScreen.kt
package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeightScreen(viewModel: QuestionnaireViewModel, navController: NavHostController) {
    var height by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 4 of 8",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "How Tall Are You?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Input Field Section
                TextField(
                    value = height,
                    onValueChange = { input ->
                        height = input
                        val heightInt = input.toIntOrNull()
                        isValid = heightInt != null && heightInt in 60..220
                    },
                    placeholder = { Text("Enter your height") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFA5D6A7), // Light green background
                        focusedIndicatorColor = if (isValid) Color.Transparent else MaterialTheme.colorScheme.error, // Red border if invalid
                        unfocusedIndicatorColor = if (isValid) Color.Transparent else MaterialTheme.colorScheme.error, // Red border if invalid
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 8.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CM",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 22.sp
                )
            }
            // Display error message if input is invalid
            if (!isValid && height.isNotEmpty()) {
                Text(
                    text = "Please enter a height between 60 and 220 cm.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Navigation Button
        Button(
            onClick = {
                val heightInt = height.toIntOrNull()
                if (heightInt != null && heightInt in 60..220) { // Validate the height
                    viewModel.setHeight(heightInt) // Save the height
                    navController.navigate("weight_screen")
                }
            },
            enabled = isValid && height.isNotEmpty(), // Enable only if input is valid and not empty
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
        ) {
            Text(
                text = "Next Step",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
