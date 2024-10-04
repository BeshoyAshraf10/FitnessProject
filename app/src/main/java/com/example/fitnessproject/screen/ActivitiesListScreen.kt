package com.example.fitnessproject.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.model.Activity

@Composable
fun ActivitiesListScreen(
    activities: List<Activity>,
    modifier: Modifier = Modifier
) {
    LazyColumn (
    modifier = modifier
    ){
        items(activities) { activity: Activity ->
            ActivityItem(activity)
        }
    }


}

@Composable
fun ActivityItem(activity: Activity, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column (
        modifier = modifier
            .clickable {

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

@Composable
fun ActivityTypes(modifier: Modifier = Modifier) {
    
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun ActivityCardPreview() {
    ActivitiesListScreen(ActivitiesData().getActivitiesNames())
}