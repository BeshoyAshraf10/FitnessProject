package com.example.fitnessproject.activityApi

import com.example.fitnessproject.model.Activity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ActivityCallable {
    @Headers(
        "x-rapidapi-key: 0566472f45msh93f47ec96806080p18bb8cjsnc3249ba88d88",
        "x-rapidapi-host: calories-burned-by-api-ninjas.p.rapidapi.com"
    )
    @GET("/v1/caloriesburned")
    fun getActivities(@Query("activity") activity: String): Call<List<Activity>>
}