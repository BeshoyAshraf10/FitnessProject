package com.example.fitnessproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.screen.Activities.ActivitiesListScreen
import com.example.fitnessproject.screen.Activities.ActivityTypes
import com.example.fitnessproject.screen.calorieCalculator.CalorieCalculatorScreen2
import com.example.fitnessproject.screen.Activities.FinishActivityScreen
import com.example.fitnessproject.screen.calorieCalculator.InformationScreen
import com.example.fitnessproject.screen.Activities.StartActivityScreen
import com.example.fitnessproject.viewModel.TimerViewModel

object Routes {
    const val FIRST_SCREEN = "first_screen"
    const val SECOND_SCREEN = "second_screen"
    const val ACT_LIST = "ActivitiesList"
    const val ACT_TYPES = "ActivityType"
    const val START_ACTIVITY = "StartActivity"
    const val FINISH_ACTIVITY = "FinishActivity"
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
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
        composable(route = Routes.ACT_LIST) { ActivitiesListScreen(
            ActivitiesData().getActivitiesNames(),
            navController = navController,
            modifier = modifier
        ) }
        composable(
            route = "$Routes.ACT_TYPES/{activity}",
            arguments = listOf(
                navArgument("activity") {type = NavType.StringType} )
        ) {
            val activity = it.arguments?.getString("activity")
            ActivityTypes(activity!!,navController,modifier = modifier )
        }
        composable(
            route = "$Routes.START_ACTIVITY/{activity}",
            arguments = listOf(
                navArgument("activity") {type = NavType.StringType} )
        ) {
            val activity = it.arguments?.getString("activity")
            StartActivityScreen(activity!!, TimerViewModel(),navController,modifier = modifier)
        }
        composable(
            route = "$Routes.FINISH_ACTIVITY/{time}/{calories}",
            arguments = listOf(
                navArgument("time") {type = NavType.LongType},
                navArgument("calories") {type = NavType.IntType}
            )
        ){
            val time = it.arguments?.getLong("time")
            val calories = it.arguments?.getInt("calories")
            FinishActivityScreen(time!!,calories!!,navController,modifier = modifier)
        }
    }
}
