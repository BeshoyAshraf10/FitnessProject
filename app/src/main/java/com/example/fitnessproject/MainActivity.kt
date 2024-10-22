package com.example.fitnessproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.navigation.AppNavHost
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.notification.setAlarm
import com.example.fitnessproject.ui.theme.FitnessProjectTheme
import com.example.fitnessproject.viewModel.ActivityViewModel

class MainActivity() : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var activityViewModel: ActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessProjectTheme {
                navController = rememberNavController()
                activityViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding->
                    AppNavHost(navController, viewModel(),modifier = Modifier
                        .padding(innerPadding))
                    Log.d("Colors", "${MaterialTheme.colorScheme}")
                }
            }
        }
    }

    override fun onPause() {

        super.onPause()
        val currentDestination = navController.currentBackStackEntry?.destination?.route

        // Example: Send a notification if the user is on the 'HOME_SCREEN'
        if (currentDestination == Routes.START_ACTIVITY) {
            setAlarm(this,activityViewModel.selectedActivity!!.name,"Activity on going")
        }
    }


}

