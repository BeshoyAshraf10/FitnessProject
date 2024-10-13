package com.example.fitnessproject.model

data class User (
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val weight: Int = 0,
    val height: Int = 0,
    val age: Int = 0,
    val gender: String = "",
    val bmi: Double = 0.0,
    val bmr: Double = 0.0,
    val activityLevel: String = "",
    val calories: Int = 0

)