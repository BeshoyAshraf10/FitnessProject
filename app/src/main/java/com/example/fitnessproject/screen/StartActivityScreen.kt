//package com.example.fitnessproject.screen
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Card
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.delay
//
//@Composable
//fun StartActivityScreen(modifier: Modifier = Modifier) {
//    // Mutable state to keep track of the remaining time
//    var timeSecond by remember { mutableStateOf(0) } // 60 seconds countdown
//    var timeMinute by remember { mutableStateOf(0) } // 60 seconds countdown
//    var isStarted by remember { mutableStateOf(false) } // Timer running state
//    // Start the timer using a coroutine
//    LaunchedEffect(isStarted) {
//        if (isStarted) {
//            delay(1000L) // Delay 1 second
//            timeSecond += 1
//        }
//    }
//    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
//        Column(
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            Card(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                    .weight(0.5f)
//            ) {
//                Column (modifier = modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center) {
//                    Text(
//                        text = "Time",
//                        fontSize = 20.sp,
//                    )
//                    Text(
//                        text = "$time",
//                        fontSize = 50.sp,
//                        modifier = Modifier
//                    )
//                }
//            }
//            Card(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                    .weight(0.5f)
//            ) {
//
//            }
//        }
//
//    }
//
//
//}
//
//@Preview(showSystemUi = true)
//@Composable
//fun TimerPreview() {
//    StartActivityScreen()
//}