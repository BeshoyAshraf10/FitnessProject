package com.example.fitnessproject.screen.Activities

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessproject.localDB.DateConverter
import com.example.fitnessproject.viewModel.ActivityViewModel

@Composable
fun FinishActivityScreen(
    timerValue: Long,
    caloriesBurned: Int,
    timeStarted: Long,
    timeEnded: Long,
    navController: NavController,
    modifier: Modifier = Modifier,
    activityViewModel: ActivityViewModel = viewModel()
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(0.5f)
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Time Taken",
                        fontSize = 20.sp,
                    )
                    Text(
                        text = timerValue.formatTime(),
                        fontSize = 50.sp,
                        modifier = Modifier
                    )
                }
            }
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(0.5f)
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Calories Burned",
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "$caloriesBurned kcal",
                        fontSize = 50.sp,
                        modifier = Modifier
                    )
                }

            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp),
                    onClick = {
                        saveActivitySession(
                            activityViewModel,
                            timeStarted,
                            timeEnded,
                            timerValue,
                            caloriesBurned
                        )
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Save",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }


        }

    }

}


fun saveActivitySession(
    viewModel: ActivityViewModel,
    timeStarted: Long,
    timeEnded: Long,
    timerValue: Long,
    caloriesBurned: Int
) {

    try {
        val currentAct = viewModel.selectedActivity
        currentAct?.timeStarted = DateConverter().fromTimestamp(timeStarted)!!
        currentAct?.timeEnded = DateConverter().fromTimestamp(timeEnded)!!
        currentAct?.durationTaken = timerValue
        currentAct?.caloriesBurned = caloriesBurned
        viewModel.upsertActivity(currentAct!!)
        Toast.makeText(viewModel.getApplication(), "Activity Saved!", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(viewModel.getApplication(), "Error Saving Activity!", Toast.LENGTH_LONG)
            .show()
        Log.d("error", e.toString())
    }

}