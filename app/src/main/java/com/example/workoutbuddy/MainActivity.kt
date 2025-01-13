package com.example.workoutbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutbuddy.screens.*
import com.example.workoutbuddy.ui.theme.WorkoutBuddyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutBuddyTheme {
                // Top-level surface for theming
                Surface {
                    // Create a single instance of the ViewModel
                    val viewModel = remember { QuestionnaireViewModel() }

                    // Set up navigation
                    val navController = rememberNavController()

                    // Build your NavHost
                    NavHost(
                        navController = navController,
                        startDestination = "name_screen"
                    ) {
                        composable("name_screen") {
                            NameScreen(viewModel, navController)
                        }
                        composable("gender_screen") {
                            // Pass the same viewModel if you want to
                            GenderSelectionScreen(viewModel, navController)
                        }
                        composable("age_screen") {
                            AgeScreen(viewModel, navController)
                        }
                        composable("height_screen") {
                            HeightScreen(viewModel, navController)
                        }
                        composable("fitness_goal_screen") {
                            FitnessGoalScreen(viewModel, navController)
                        }
                        composable("weight_screen") {
                            WeightScreen(viewModel, navController)
                        }
                        composable("workout_frequency_screen") {
                            WorkoutFrequencyScreen(viewModel, navController)
                        }
                        composable("workout_duration_screen") {
                            WorkoutDurationScreen(viewModel, navController)
                        }
                        composable("workout_plan_screen") {
                            // Some hypothetical final screen that shows the plan
                            WorkoutPlanScreen(viewModel, navController)
                        }
                        composable("day_detail_screen") {
                            // Some hypothetical detail screen that shows the plan
                            DayDetailScreen(navController)
                        }
                    }
                }
            }
        }

        // Example: If you still want to do an API call at app start
        // (but your key is invalid right now)
        lifecycleScope.launch {
            //ApiHelper.fetchWorkoutPlan(viewModel.answers)
        }
    }
}
