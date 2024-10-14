package com.example.fitnessproject.screen.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.R
import com.example.fitnessproject.StepsActivity
import com.example.fitnessproject.components.BottomNavigationBar
import com.example.fitnessproject.navigation.Routes



@Composable
fun HomeScreen(navController: NavController) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(1) }
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItemIndex = selectedItemIndex,
                onItemSelected = {
                    selectedItemIndex = it
                },
                navController = navController
            )
        }
    ) { innerPadding ->

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CardItem(title = "Steps", imageRes = R.drawable.steps, screenWidth) {
                context.startActivity(Intent(context, StepsActivity::class.java))
            }
            Spacer(modifier = Modifier.height(16.dp))
            CardItem(title = "Activities", imageRes = R.drawable.activities, screenWidth) {
                navController.navigate(Routes.ACT_SESSIONS)
            }
//            Spacer(modifier = Modifier.height(16.dp))
//            CardItem(
//                title = "Calories & Nutrition",
//                imageRes = R.drawable.nutritionjpg,
//                screenWidth
//            ) {
//
//            }

            Spacer(modifier = Modifier.height(16.dp))
            CardItem(title = "Update Data", imageRes = R.drawable.weight, screenWidth) {
                navController.navigate(Routes.FIRST_SCREEN)
            }
        }
    }
}

// Bottom navigation bar composable with the list embedded


// CardItem remains the same
@Composable
fun CardItem(title: String, imageRes: Int, screenWidth: Dp, onNavigate: () -> Unit) {
    val cardHeight = screenWidth * 0.4f
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        onClick = onNavigate
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color(0x40000000))
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomePrev() {
    HomeScreen(rememberNavController())

}
