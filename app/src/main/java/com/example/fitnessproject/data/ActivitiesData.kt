package com.example.fitnessproject.data

import com.example.fitnessproject.R
import com.example.fitnessproject.model.Activity

class ActivitiesData {

    fun getActivitiesNames() = listOf(
        Activity("Football", R.drawable.ic_soccer),
        Activity("Basketball", R.drawable.ic_basketball),
        Activity("Cricket", R.drawable.ic_cricket),
        Activity("Tennis", R.drawable.ic_tennis),
        Activity("Running", R.drawable.ic_running),
        Activity("Walking", R.drawable.ic_walking),
        Activity("Cycling", R.drawable.ic_cycling),
        Activity("Hiking", R.drawable.ic_hiking),
        Activity("Swimming", R.drawable.ic_swimming),
        Activity("Weight Lifting", R.drawable.ic_weight)
    )
}