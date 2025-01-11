package com.example.workoutbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeightScreen(navController: NavHostController) {
    var height by remember { mutableStateOf("") }

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
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "How Tall Are You?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input Field Section
            TextField(
                value = height,
                onValueChange = { height = it },
                placeholder = { Text("Enter your height") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFA5D6A7), // Light green background
                    focusedIndicatorColor = Color.Transparent, // Text color when focused
                    unfocusedIndicatorColor = Color.Transparent, // Text color when unfocused
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                singleLine = true
            )

            Text(
                text = "CM",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            )
        }

        // Navigation Button
        Button(
            onClick = {
                //if (height.isNotEmpty()) { UNCOMMENT THIS LINE
                    navController.navigate("weight_screen") // Navigate to the next screen
                //} UNCOMMENT THIS LINE
            },
            modifier = Modifier
                .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                .height(50.dp) // Increased button height for a "fatter" appearance
                //.offset(y = (-96).dp) // Move the button up by 96dp
        ) {
            Text("Next Step")
        }
    }
}