package com.example.fitnessproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.fitnessproject.screen.LoginScreen
import com.example.fitnessproject.screen.SignUpScreen
import com.example.fitnessproject.screen.home.HomeScreen
import com.example.fitnessproject.screen.profile.ProfileScreen
import com.example.fitnessproject.viewModel.ActivityViewModel
import com.example.fitnessproject.viewModel.TimerViewModel

object Routes {
    const val HOME_SCREEN = "home"
    const val PROFILE_SCREEN = "profile"
    const val LOGIN = "login_screen"
    const val SIGNUP = "signUp_screen"
    const val FIRST_SCREEN = "first_screen"
    const val SECOND_SCREEN = "second_screen"
    const val ACT_LIST = "ActivitiesList"
    const val ACT_TYPES = "ActivityType"
    const val START_ACTIVITY = "StartActivity"
    const val FINISH_ACTIVITY = "FinishActivity"
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier, activityViewModel: ActivityViewModel = viewModel()) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Routes.PROFILE_SCREEN) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(navController)
        }
        composable(Routes.HOME_SCREEN){
            HomeScreen(navController)
        }
        composable(Routes.PROFILE_SCREEN){
            ProfileScreen()
        }

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
            route = "${Routes.ACT_TYPES}/{activity}/{icon}",
            arguments = listOf(
                navArgument("activity") {type = NavType.StringType},
                navArgument("icon") {type = NavType.IntType}
            )
        ) {
            val activity = it.arguments?.getString("activity")
            val icon = it.arguments?.getInt("icon")
            ActivityTypes(activity!!,icon!!,navController,modifier = modifier,activityViewModel = activityViewModel)
        }
//
        composable(
            route = Routes.START_ACTIVITY,
        ) {
            StartActivityScreen( TimerViewModel(),navController,modifier = modifier,activityViewModel = activityViewModel)
        }
        composable(
            route = "${Routes.FINISH_ACTIVITY}/{time}/{calories}/{timeStarted}/{timeEnded}",
            arguments = listOf(
                navArgument("time") {type = NavType.LongType},
                navArgument("calories") {type = NavType.IntType},
                navArgument("timeStarted") {type = NavType.LongType},
                navArgument("timeEnded") {type = NavType.LongType}
            )
        ){
            val time = it.arguments?.getLong("time")
            val calories = it.arguments?.getInt("calories")
            val timeStarted = it.arguments?.getLong("timeStarted")
            val timeEnded = it.arguments?.getLong("timeEnded")
            FinishActivityScreen(time!!,calories!!,timeStarted!!,timeEnded!!,navController,modifier = modifier,activityViewModel = activityViewModel)
        }
    }
}
