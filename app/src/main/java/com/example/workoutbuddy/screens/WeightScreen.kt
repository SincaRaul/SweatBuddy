// WeightScreen.kt
package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(viewModel: QuestionnaireViewModel, navController: NavHostController) {
    var weight by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }

    val isButtonEnabled = weight.isNotEmpty() && isValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 5 of 8",
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
                text = "Your Weight",
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
                    value = weight,
                    onValueChange = { input ->
                        weight = input
                        val weightInt = input.toIntOrNull()
                        isValid = weightInt != null && weightInt in 30..300
                    },
                    placeholder = { Text("Enter your weight") },
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
                Spacer(modifier = Modifier.width(8.dp)) // space between text field and KG
                Text(
                    text = "KG",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 22.sp
                )
            }

            // Display error message if input is invalid
            if (!isValid && weight.isNotEmpty()) {
                Text(
                    text = "Please enter a weight between 30 and 300 kg.",
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
                val weightInt = weight.toIntOrNull()
                if (weightInt != null && weightInt in 30..300) {
                    viewModel.setWeight(weightInt)
                    navController.navigate("fitness_goal_screen")
                }
            },
            enabled = isButtonEnabled, // Enable only if input is valid and not empty
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
