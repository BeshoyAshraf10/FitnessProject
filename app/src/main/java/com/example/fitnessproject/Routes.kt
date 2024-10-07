package com.example.fitnessproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitnessproject.Routes
import com.example.fitnessproject.Routes.ACT_LIST
import com.example.fitnessproject.Routes.ACT_TYPES
import com.example.fitnessproject.Routes.FINISH_ACTIVITY
import com.example.fitnessproject.Routes.START_ACTIVITY
import com.example.fitnessproject.data.ActivitiesData
import com.example.fitnessproject.model.Activity
import com.example.fitnessproject.screen.ActivitiesListScreen
import com.example.fitnessproject.screen.ActivityTypes
import com.example.fitnessproject.screen.FinishActivityScreen
import com.example.fitnessproject.screen.StartActivityScreen
import com.example.fitnessproject.viewModel.TimerViewModel

object Routes{
    const val ACT_LIST = "ActivitiesList"
    const val ACT_TYPES = "ActivityType"
    const val START_ACTIVITY = "StartActivity"
    const val FINISH_ACTIVITY = "FinishActivity"
}


@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ACT_LIST){
        composable(route = ACT_LIST) { ActivitiesListScreen(
            ActivitiesData().getActivitiesNames(),
            navController = navController,
            modifier = modifier
        ) }
        composable(
            route = "$ACT_TYPES/{activity}",
            arguments = listOf(
                navArgument("activity") {type = NavType.StringType} )
        ) {
            val activity = it.arguments?.getString("activity")
            ActivityTypes(activity!!,navController,modifier = modifier )
        }
        composable(
            route = "$START_ACTIVITY/{activity}",
            arguments = listOf(
                navArgument("activity") {type = NavType.StringType} )
        ) {
            val activity = it.arguments?.getString("activity")
            StartActivityScreen(activity!!, TimerViewModel(),navController,modifier = modifier)
        }
        composable(
            route = "$FINISH_ACTIVITY/{time}/{calories}",
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