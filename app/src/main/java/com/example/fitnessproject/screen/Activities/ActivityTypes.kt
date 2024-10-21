package com.example.fitnessproject.screen.Activities

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessproject.activityApi.ActivityCallable
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.model.Activity
import com.example.fitnessproject.viewModel.ActivityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun ActivityTypes(
    activityName: String,
    icon: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    activityViewModel: ActivityViewModel = viewModel()
) {
    var activities by remember { mutableStateOf(listOf<Activity>()) }
//    val activityObj = ActivitiesData().parseActivityFromString(activityString)
//    val activityName = activityObj?.name ?: ""
    LaunchedEffect(activityName) {
        Log.d("trace", "Activity name: $activityName")
        loadActivityTypes(activityName) {
            activities = it
        }
    }
    Scaffold (modifier = Modifier.fillMaxSize()){innerPadding ->
        if (activities.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item{
                    Text(text = activityName,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp))
                }
                items(activities) { activity ->
                    activity.icon = icon
                    ActivityItem(activity) {
                        activityViewModel.selectActivity(activity)
                        navController.popBackStack()
                        navController.navigate(Routes.START_ACTIVITY)





                    }
                }
            }
        } else {
            Box(modifier = modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = "Loading activities...", fontSize = 20.sp)

            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ActivityTypesPrev() {
//    ActivityTypes("running", rememberNavController())
}

fun loadActivityTypes(activityName: String, callback: (List<Activity>) -> Unit) {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://calories-burned-by-api-ninjas.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val callable = retrofit.create(ActivityCallable::class.java)
    callable.getActivities(activityName).enqueue(object : Callback<List<Activity>> {
        override fun onResponse(call: Call<List<Activity>>, response: Response<List<Activity>>) {
            if (response.isSuccessful) {
                val activities = response.body() ?: emptyList()
                callback(activities) // Pass the data to the callback
            } else {val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.d("trace", "Error: $errorBody")
            }
        }

        override fun onFailure(call: Call<List<Activity>>, t: Throwable) {
            Log.d("trace", "Error: ${t.message}")
        }
    })
}

