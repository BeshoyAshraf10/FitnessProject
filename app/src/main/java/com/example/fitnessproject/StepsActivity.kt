package com.example.fitnessproject

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.fitnessproject.ui.theme.StepsTheme
import com.example.fitnessproject.ui.theme.TDEEBabyBlue
import com.example.fitnessproject.viewModel.TimerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class StepsActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private val stepCount = MutableStateFlow(0)
    private val timerViewModel: TimerViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)


        @OptIn(ExperimentalPermissionsApi::class)
        setContent {
            StepsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val currentStepCount by stepCount.collectAsState()
                    val timerValue by timerViewModel.timer.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.4f)
                                .padding(bottom = 16.dp)

                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {

                                val activityRecognitionPermission = rememberPermissionState(
                                    permission = android.Manifest.permission.ACTIVITY_RECOGNITION
                                )

                                when (val status = activityRecognitionPermission.status) {
                                    is PermissionStatus.Granted -> {
                                        // Permission is granted; register sensor listener
                                        LaunchedEffect(Unit) { registerSensor() }
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Steps",
                                                fontFamily = FontFamily(Font(R.font.calibri_regular)),
                                                fontSize = 24.sp,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                            Text(
                                                text = currentStepCount.toString(),
                                                fontFamily = FontFamily(Font(R.font.calibri_bold)),
                                                fontSize = 36.sp
                                            )
                                        }
                                    }

                                    is PermissionStatus.Denied -> {
                                        val shouldShowRationale = status.shouldShowRationale

                                        if (shouldShowRationale) {
                                            Text(
                                                text = "This app requires access to your physical activity to count steps."
                                            )
                                        } else {
                                            Text(
                                                text = "Step counting permission denied. Please enable it in settings.",
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                activityRecognitionPermission.launchPermissionRequest()
                                            },
                                            modifier = Modifier.padding(top = 16.dp)
                                        ) {
                                            Text(text = "Grant Permission")
                                        }
                                    }
                                }
                            }

                        }
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.2f)
                                .padding(bottom = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                timerViewModel.startTimer()
                                Stepsinfo(
                                    R.drawable.clock,
                                    timerValue.formatTime(),
                                    "time",
                                    "Time"
                                )
                                Stepsinfo(
                                    R.drawable.calories_icon,
                                    calculateCalories(currentStepCount).toString(),
                                    "KCal",
                                    "calories"
                                )
                                Stepsinfo(
                                    R.drawable.ic_running,
                                    String.format("%.2f", calculateDistance(currentStepCount)),
                                    "m",
                                    "distance"
                                )
                            }
                        }

                    }
                }
            }
        }

    }


    @OptIn(ExperimentalPermissionsApi::class)
    private fun registerSensor() {
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
                stepCount.update { current -> current + it.values.size }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}

@Composable
fun Stepsinfo(@DrawableRes icon: Int, info: String, unit: String, contentDesc: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = contentDesc,
            modifier = Modifier
                .size(36.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = info,
            fontFamily = FontFamily(Font(R.font.calibri_bold)),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = unit,
            fontFamily = FontFamily(Font(R.font.calibri_regular)),
            fontSize = 16.sp
        )
    }
}

fun calculateCalories(steps: Int) = (0.04 * steps).toInt()
fun calculateDistance(steps: Int) = (0.762 * steps).toDouble()

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}