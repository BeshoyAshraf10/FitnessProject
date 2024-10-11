package com.example.fitnessproject.screen.Activities

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.model.Activity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivitiesListScreen(
    activities: List<Activity>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Activities",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
            }
            items(activities) { activity: Activity ->
                ActivityItem(activity) {
                    navController.navigate("${Routes.ACT_TYPES}/${activity.name}/${activity.icon}")
//                    navController.navigate(Routes.FIRST_SCREEN)
                }
            }
        }
    }


}


@Composable
fun ActivityItem(activity: Activity, modifier: Modifier = Modifier, onNavigate: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable {
                onNavigate()
            }
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
                if(activity.caloriesPerHour != 0){
                    Text(
                        text = "${activity.caloriesPerHour.toString()} cal/hour",
                        fontSize = 16.sp,
                        modifier = modifier
                            .align(Alignment.End)

                    )
                }
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


@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun ActivityCardPreview() {
    ActivitiesListScreen(ActivitiesData().getActivitiesNames(), rememberNavController())
}