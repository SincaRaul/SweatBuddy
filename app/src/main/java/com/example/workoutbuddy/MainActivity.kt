package com.example.workoutbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutbuddy.ui.theme.WorkoutBuddyTheme
import androidx.compose.ui.graphics.Color






class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutBuddyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "name_screen"
    ) {
        composable("name_screen") { NameScreen(navController) }
        composable("gender_screen") { GenderSelectionScreen(navController) }
        composable("age_screen") { AgeScreen(navController) }
    }
}

/*@Composable
fun NameScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Personal Details",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        // TextField for "Your Name"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Your Name")
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter your name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        // Next Button
        Button(
            onClick = {
                navController.navigate("gender_selection")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Step 1 of 5",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Enter Your Name",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)

        )

        // Input Field Section
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Your Name") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFA5D6A7), // Light green background
                focusedIndicatorColor  =  Color.Transparent, // Text color when focused
                unfocusedIndicatorColor  =  Color.Transparent, // Text color when unfocused
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            singleLine = true
        )

        // Navigation Button
        Button(
            onClick = {
                if (name.isNotEmpty()) {
                    navController.navigate("gender_screen")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.5f) // Button width reduced to 50% of the screen
                .height(50.dp) // Increased button height for a "fatter" appearance
        ) {
            Text("Next Step")
        }
    }
}


@Composable
fun GenderSelectionScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Personal Details",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        // Gender Selection Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Handle Male Selection
                    //Male = true IMPLEMENT
                    navController.navigate("age_screen")
                },
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                Text(text = "Male")
            }

            Button(
                onClick = {
                    // Handle Female Selection
                    // Female = true IMPLEMENT
                    navController.navigate("age_screen")
                },
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                Text(text = "Female")
            }
        }

        // Back Button
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back")
        }
    }
}


@Composable
fun AgeScreen(navController: NavHostController) {
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Personalisation",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        // TextField for "Your Name"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "How Young Are You?")
            TextField(
                value = age,
                onValueChange = { age = it },
                placeholder = { Text("Enter your age") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        // Next Button
        Button(
            onClick = {
                navController.navigate("height_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

