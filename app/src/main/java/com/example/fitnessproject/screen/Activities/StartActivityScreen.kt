package com.example.fitnessproject.screen.Activities

import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessproject.Routes
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.localDB.DateConverter
import com.example.fitnessproject.viewModel.ActivityViewModel
import com.example.fitnessproject.viewModel.TimerViewModel
import java.util.Date

@Composable
fun StartActivityScreen(
    timerViewModel: TimerViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    activityViewModel: ActivityViewModel = viewModel()
) {
    Log.d("trace", "Activity obj: ${activityViewModel.selectedActivity}")
    val activityObj = activityViewModel.selectedActivity
    val timerValue by timerViewModel.timer.collectAsState()
    var timeStarted by remember { mutableStateOf(Date()) }
    var timeEnded by remember { mutableStateOf(Date()) }
    var isStarted by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var caloriesPerSecond by remember { mutableStateOf(calculateCaloriesPerSecond(activityObj!!.caloriesPerHour)) }
    var caloriesBurned by remember { mutableStateOf(0) }
    LaunchedEffect(timerValue) {
        caloriesBurned = (timerValue * caloriesPerSecond).toInt()
    }

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
                        text = "Time",
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
                if (isStarted) {
                    Button(
                        modifier = modifier
                            .weight(0.5f)
                            .padding(end = 8.dp),
                        onClick = {
                            isPaused = !isPaused
                            if (isPaused){ timerViewModel.pauseTimer() }
                            else{ timerViewModel.startTimer() }
                        }
                    ) {
                        Text(text = if (isPaused) "Resume" else "Pause",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center)
                    }
                    Button(
                        modifier = modifier
                            .weight(0.5f)
                            .padding(start = 8.dp),
                        onClick = {
                            timeEnded = Date()
                            timerViewModel.stopTimer()
                            navController.popBackStack()
                            navController.navigate("${Routes.FINISH_ACTIVITY}/${timerValue}/${caloriesBurned}/${DateConverter().dateToTimestamp(timeStarted)}/${DateConverter().dateToTimestamp(timeEnded)}")
                        }
                    ) {
                        Text(text = "Finish",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center)
                    }
                }else{
                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 80.dp),
                        onClick = {
                            isStarted = true
                            timerViewModel.startTimer()
                        }
                    ){
                        Text(text = "Start",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center)
                    }
                }

            }
        }

    }


}

@Preview(showSystemUi = true)
@Composable
fun TimerPreview() {
//    StartActivityScreen(TimerViewModel())
}
fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun calculateCaloriesPerSecond(caloriesPerHour: Int): Double {
    return caloriesPerHour / 3600.0
}