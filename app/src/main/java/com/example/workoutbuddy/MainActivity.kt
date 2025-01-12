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
import com.example.workoutbuddy.screens.NameScreen
import com.example.workoutbuddy.screens.GenderSelectionScreen
import com.example.workoutbuddy.screens.AgeScreen
import com.example.workoutbuddy.screens.HeightScreen
import com.example.workoutbuddy.screens.FitnessGoalScreen
import com.example.workoutbuddy.screens.WeightScreen
import com.example.workoutbuddy.screens.WorkoutFrequencyScreen
import com.example.workoutbuddy.screens.WorkoutDurationScreen
import androidx.lifecycle.lifecycleScope
import com.example.workoutbuddy.api.ApiHelper
import kotlinx.coroutines.launch




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
        lifecycleScope.launch {
            ApiHelper.testApiCall()
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
            composable("height_screen") { HeightScreen(navController) }
            composable("fitness_goal_screen") { FitnessGoalScreen(navController) }
            composable("weight_screen") { WeightScreen(navController) }
            composable("workout_frequency_screen") { WorkoutFrequencyScreen(navController) }
            composable("workout_duration_screen") { WorkoutDurationScreen(navController) }
        }
    }

