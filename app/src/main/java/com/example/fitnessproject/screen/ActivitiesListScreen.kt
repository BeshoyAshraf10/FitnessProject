package com.example.fitnessproject.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.model.Activity
import com.example.fitnessproject.Routes.ACT_LIST
import com.example.fitnessproject.Routes.ACT_TYPES

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivitiesListScreen(
    activities: List<Activity>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            items(activities) { activity: Activity ->
                ActivityItem(activity) {
                    navController.navigate("$ACT_TYPES/$activity")
                }
            }
        }
    }


}

@Composable
fun ActivitiesList(
    activities: List<Activity>,
    modifier: Modifier = Modifier) {
    
}

@Composable
fun ActivityItem(activity: Activity, modifier: Modifier = Modifier,onNavigate: () -> Unit) {
    val context = LocalContext.current
    Column (
        modifier = modifier
            .clickable {
                onNavigate()
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(vertical = 12.dp)

        ) {
            Icon(
                painter = painterResource(activity.icon),
                contentDescription = activity.name,
                modifier = modifier
                    .padding(start = 8.dp)
                    .size(30.dp)
            )
            Text(
                text = activity.name,
                fontSize = 22.sp,
                modifier = modifier
                    .padding(start = 16.dp)

            )
        }
        HorizontalDivider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }

}



@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun ActivityCardPreview() {
//    ActivitiesListScreen(ActivitiesData().getActivitiesNames())
}