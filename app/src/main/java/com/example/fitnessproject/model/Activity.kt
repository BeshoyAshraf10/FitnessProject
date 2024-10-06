package com.example.fitnessproject.model

import androidx.annotation.DrawableRes
import com.example.fitnessproject.R
import com.google.gson.annotations.SerializedName


data class Activity(
    val name:String,
    @DrawableRes var icon: Int = R.drawable.ic_main_sport,
    @SerializedName("calories_per_hour")
    val caloriesPerHour :Int = 0,
    @SerializedName("duration_minutes")
    val duration: Int = 0,
    @SerializedName("total_calories")
    val totalCalories:Int =0

)