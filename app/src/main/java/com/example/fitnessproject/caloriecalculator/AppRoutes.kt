package com.example.fitnessproject.caloriecalculator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object Routes {
    const val FIRST_SCREEN = "first_screen"
    const val SECOND_SCREEN = "second_screen"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.FIRST_SCREEN) {
        composable(Routes.FIRST_SCREEN) {
            InformationScreen(navController)
        }
        composable(
            route = "${Routes.SECOND_SCREEN}/{calories}/{bmr}/{bmi}",
            arguments = listOf(
                navArgument("calories") { type = NavType.IntType },
                navArgument("bmr") { type = NavType.IntType },
                navArgument("bmi") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val calories = backStackEntry.arguments?.getInt("calories")?:0
            val bmr = backStackEntry.arguments?.getInt("bmr")?:0
            val bmi = backStackEntry.arguments?.getFloat("bmi")?:0f

            CalorieCalculatorScreen2(calories = calories, bmr = bmr, bmi = bmi)
        }
    }
}
