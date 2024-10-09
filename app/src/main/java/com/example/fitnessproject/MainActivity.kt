package com.example.fitnessproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fitnessproject.caloriecalculator.AppNavigation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.screen.ActivitiesListScreen
import com.example.fitnessproject.ui.theme.FitnessProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _->
                    AppNavHost()
                }
            }
        }
    }
}

