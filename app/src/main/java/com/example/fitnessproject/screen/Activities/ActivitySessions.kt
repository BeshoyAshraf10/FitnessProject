package com.example.fitnessproject.screen.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.R
import com.example.fitnessproject.model.Activity
import com.example.fitnessproject.viewModel.ActivityViewModel
import java.util.Date

@Composable
fun ActivitySessionItem(
//    activityViewModel: ActivityViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val activity = Activity(
        id = 0, // Since the ID is auto-generated
        name = "Running, 5 mph (12 minute mile)",
        icon = R.drawable.ic_main_sport, // Assuming this is a valid drawable resource ID
        caloriesPerHour = 581,
        duration = 60,
        totalCalories = 581,
        durationTaken = 60L, // Duration in seconds (1 minute)
        caloriesBurned = 9,
        timeStarted = Date(1728582694110), // Time started
        timeEnded = Date(1728582755920)    // Time ended
    )
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)

        ) {
            Icon(
                painter = painterResource(activity.icon),
                contentDescription = activity.name,
                modifier = modifier
                    .padding(start = 8.dp)
                    .size(30.dp)
            )
            Column (
                modifier = modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = activity.name,
                    fontSize = 22.sp,
                    modifier = modifier
                        .padding(start = 16.dp)

                )

                    Text(
                        text = "${activity.caloriesPerHour.toString()} cal/hour",
                        fontSize = 16.sp,
                        modifier = modifier
                            .align(Alignment.End)

                    )
                }
            
        }
        HorizontalDivider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}

@Composable
fun ActivitySessionList(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ActivitySessionListPrev() {
    ActivitySessionItem(rememberNavController())
}