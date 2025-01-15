// MainActivity.kt
package com.example.workoutbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutbuddy.screens.AgeScreen
import com.example.workoutbuddy.screens.FitnessGoalScreen
import com.example.workoutbuddy.screens.GenderSelectionScreen
import com.example.workoutbuddy.screens.HeightScreen
import com.example.workoutbuddy.screens.NameScreen
import com.example.workoutbuddy.screens.QuestionnaireViewModel
import com.example.workoutbuddy.screens.WeightScreen
import com.example.workoutbuddy.screens.WorkoutDetailsScreen
import com.example.workoutbuddy.screens.WorkoutDurationScreen
import com.example.workoutbuddy.screens.WorkoutFrequencyScreen
import com.example.workoutbuddy.screens.WorkoutPlanScreen
import com.example.workoutbuddy.ui.theme.WorkoutBuddyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutBuddyTheme {
                Surface {
                    val navController = rememberNavController()
                    val questionnaireViewModel: QuestionnaireViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "name_screen"
                    ) {
                        composable("name_screen") {
                            NameScreen(questionnaireViewModel, navController)
                        }
                        composable("gender_screen") {
                            GenderSelectionScreen(questionnaireViewModel, navController)
                        }
                        composable("age_screen") {
                            AgeScreen(questionnaireViewModel, navController)
                        }
                        composable("height_screen") {
                            HeightScreen(questionnaireViewModel, navController)
                        }
                        composable("fitness_goal_screen") {
                            FitnessGoalScreen(questionnaireViewModel, navController)
                        }
                        composable("weight_screen") {
                            WeightScreen(questionnaireViewModel, navController)
                        }
                        composable("workout_frequency_screen") {
                            WorkoutFrequencyScreen(questionnaireViewModel, navController)
                        }
                        composable("workout_duration_screen") {
                            WorkoutDurationScreen(questionnaireViewModel, navController)
                        }
                        composable("workout_plan_screen") {
                            WorkoutPlanScreen(questionnaireViewModel, navController)
                        }
                        composable("workout_details_screen") { // Register the new screen
                            WorkoutDetailsScreen(questionnaireViewModel)
                        }
                    }
                }
            }
        }
    }
}
