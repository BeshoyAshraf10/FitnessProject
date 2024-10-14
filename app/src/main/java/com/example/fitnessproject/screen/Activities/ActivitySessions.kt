package com.example.fitnessproject.screen.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.R
import com.example.fitnessproject.model.Activity
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.screen.Activities.formatTime
import com.example.fitnessproject.viewModel.ActivityViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ActivitySessionItem(
    activity: Activity,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    ) {

            Text(
                text = activity.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
            )

                Text(
                    text = "Calories burned: ${activity.caloriesBurned} kcal",
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)

                )
                Text(
                    text = "Time Taken: ${activity.durationTaken.formatTime()}",
                    fontSize = 16.sp,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )


            Text(
                    text= "Time started: ${formatter.format(activity.timeStarted)}",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                )
            Text(
                    text= "Time ended: ${formatter.format(activity.timeEnded)}",
                fontSize = 16.sp,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp, bottom = 8.dp)
                )

//        HorizontalDivider(
//            color = Color.Black,
//            thickness = 1.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//
//        )
    }
}

@Composable
fun ActivitySessionList(navController: NavController,modifier: Modifier = Modifier,viewModel: ActivityViewModel = viewModel()) {
    val activities by viewModel.getActivities().collectAsState(initial = emptyList())
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Text(
        text = "Activity Sessions",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Button(
        onClick = {
            navController.navigate(Routes.ACT_LIST)
        }
    ) {
         Text(
            text = "Add Activity",
            fontSize = 16.sp
        )
    }
    if(activities.isNotEmpty()){
        LazyColumn {
            item {
            }
            items(activities) { activity ->
                ActivitySessionItem(
                    activity = activity,
                    navController = rememberNavController()
                )
            }
        }
    }else{
            Text(
                text = "No activities found",
                modifier = Modifier.padding(16.dp)
            )

    }

}
}

@Preview(showBackground = true, showSystemUi = true,
    device = "id:pixel_8"
)
@Composable
private fun ActivitySessionListPrev() {
    ActivitySessionItem(
        Activity(
        id = 0, // Since the ID is auto-generated
        name = "Running, 5 mph (12 minute mile)vdfdsfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsd",
        icon = R.drawable.ic_main_sport, // Assuming this is a valid drawable resource ID
        caloriesPerHour = 581,
        duration = 60,
        totalCalories = 581,
        durationTaken = 60L, // Duration in seconds (1 minute)
        caloriesBurned = 9,
        timeStarted = Date(1728582694110), // Time started
        timeEnded = Date(1728582755920)    // Time ended
    )
        ,rememberNavController())
//    ActivitySessionList(rememberNavController())
}