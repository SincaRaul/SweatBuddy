package com.example.workoutbuddy.models

data class QuestionnaireAnswers(
    var name: String = "",
    var gender: String = "",
    var age: Int = 0,
    var height: Int = 0,
    var weight: Int = 0,
    var fitnessGoal: String = "",
    var workoutFrequency: Int = 0,
    var workoutDuration: Int = 0
)

fun QuestionnaireAnswers.createPrompt(): String {
    // 1) Basic user info
    val userInfo = """
        Name: $name
        Gender: $gender
        Age: $age
        Height: $height cm
        Weight: $weight kg
        Fitness Goal: $fitnessGoal
        Workout Frequency: $workoutFrequency days/week
        Preferred Workout Duration: $workoutDuration minutes
    """.trimIndent()

    return """
        Create a personalized workout plan for the following user:
        
        $userInfo
        
        Additional details:
        - The user’s main goal is: $fitnessGoal.
        - Focus on a rep and set range per exercise appropriate for $fitnessGoal.
        - Each workout day can have up to as many exercises you see fit given the $workoutDuration minutes allocated time per workout.
        - Cover all major muscle groups across the week. Split them as you'd think given the $workoutFrequency and $workoutDuration and $fitnessGoal.
        - Provide recommended rest time between sets appropriate for $fitnessGoal.
        
        Output the plan in a structured format, listing day-by-day with exercises, sets, and reps.
        Return a JSON with a 'days' list. Each 'day' has a 'day_name' plus 'exercises' with name, sets, reps.
        You choose the exercises and structure and 'day_name' as you see fit.
        Output only valid JSON, no additional text, no disclaimers.

    """.trimIndent()
}
