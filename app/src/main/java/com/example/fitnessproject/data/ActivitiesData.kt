package com.example.fitnessproject.data

import com.example.fitnessproject.R
import com.example.fitnessproject.model.Activity

class ActivitiesData {

    fun getActivitiesNames() = listOf(
        Activity(name = "Football", icon = R.drawable.ic_soccer),
        Activity(name = "Basketball",icon = R.drawable.ic_basketball),
        Activity(name = "Cricket", icon =R.drawable.ic_cricket),
        Activity(name = "Tennis",icon = R.drawable.ic_tennis),
        Activity(name = "Running",icon = R.drawable.ic_running),
        Activity(name = "Walking",icon = R.drawable.ic_walking),
        Activity(name = "Cycling",icon = R.drawable.ic_cycling),
        Activity(name = "Hiking", icon =R.drawable.ic_hiking),
        Activity(name = "Swimming",icon = R.drawable.ic_swimming),
        Activity(name = "Weight Lifting",icon = R.drawable.ic_weight)
    )

    fun parseActivityFromString(activityString: String): Activity? {
        val regex = """Activity\(name=(.*?), icon=(\d+), caloriesPerHour=(\d+), duration=(\d+), totalCalories=(\d+)\)""".toRegex()
        val matchResult = regex.find(activityString)

        return if (matchResult != null) {
            val (name, icon, caloriesPerHour, duration, totalCalories) = matchResult.destructured
            Activity(
                name = name,
                icon = icon.toInt(),
                caloriesPerHour = caloriesPerHour.toInt(),
                duration = duration.toInt(),
                totalCalories = totalCalories.toInt()
            )
        } else {
            null
        }
    }
}