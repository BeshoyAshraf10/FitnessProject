package com.example.fitnessproject.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.fitnessproject.R
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.util.Date

@Entity(tableName = "activity")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String,
    @DrawableRes var icon: Int = R.drawable.ic_main_sport,
    @SerializedName("calories_per_hour")
    val caloriesPerHour :Int = 0,
    @SerializedName("duration_minutes")
    val duration: Int = 0,
    @SerializedName("total_calories")
    val totalCalories:Int =0,
    @ColumnInfo(name = "duration_taken")
    var durationTaken: Long = 0, // Store time as seconds,
    @ColumnInfo(name = "calories_burned")
    var caloriesBurned: Int = 0,
    @ColumnInfo(name = "time_started")
    var timeStarted: Date = Date(),  // Time started
    @ColumnInfo(name = "time_ended")
    var timeEnded: Date = Date()     // Time ended

)

