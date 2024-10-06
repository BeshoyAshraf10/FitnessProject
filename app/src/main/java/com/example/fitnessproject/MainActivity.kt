package com.example.fitnessproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fitnessproject.caloriecalculator.AppNavigation
import com.example.fitnessproject.ui.theme.FitnessProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessProjectTheme {
                AppNavigation()
            }
        }
    }
}
