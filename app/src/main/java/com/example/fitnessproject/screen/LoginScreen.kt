package com.example.fitnessproject.screen

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessproject.data.login.LoginViewModel
import com.example.fitnessproject.R
import com.example.fitnessproject.components.*
import com.example.fitnessproject.data.login.LoginUIEvent
import com.example.fitnessproject.navigation.PostOfficeAppRouter
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.navigation.Screen
import com.example.fitnessproject.navigation.SystemBackButtonHandler
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController,loginViewModel: LoginViewModel = viewModel()) {

    val context = LocalContext.current
    LaunchedEffect(loginViewModel.loginInProgress.value){
        if (!loginViewModel.loginInProgress.value) {
            if (loginViewModel.isLoginSuccessful.value) {
                navController.popBackStack()
                navController.navigate(Routes.HOME_SCREEN)
            } else if (loginViewModel.loginError.value.isNotEmpty()) {
                Toast.makeText(
                    context,
                    loginViewModel.loginError.value,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
    LaunchedEffect (loginViewModel.isForgetPassEmailSent.value){
        if (loginViewModel.isForgetPassEmailSent.value) {
            Toast.makeText(
                context,
                "Email sent successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.message),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(40.dp))
                UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password)){
                    loginViewModel.onEvent(LoginUIEvent.ForgotPasswordClicked)
                }

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)

                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
//                    PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                    navController.navigate(Routes.SIGNUP)
                })
            }
        }

        if(loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }


//    SystemBackButtonHandler {
//        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
//    }
}

@Preview
@Composable
fun LoginScreenPreview() {
//    LoginScreen()
}

@Composable
fun LoadingScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    // LaunchEffect to check the authentication state once when this screen is launched
    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is logged in, navigate to home screen
            navController.navigate(Routes.HOME_SCREEN) {
                popUpTo(Routes.LOADING_SCREEN) { inclusive = true }
            }
        } else {
            // User is not logged in, navigate to login screen
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.LOADING_SCREEN) { inclusive = true }
            }
        }
    }

    // Simple UI with a progress indicator while checking login state
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}