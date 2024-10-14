package com.example.fitnessproject.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.data.signup.SignupViewModel
import com.example.fitnessproject.R
import com.example.fitnessproject.components.*
import com.example.fitnessproject.data.signup.SignupUIEvent
import com.example.fitnessproject.navigation.PostOfficeAppRouter
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.navigation.Screen
import com.example.fitnessproject.navigation.SystemBackButtonHandler


@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(signupViewModel.signUpInProgress.value) {
        if (!signupViewModel.signUpInProgress.value) {
            if (signupViewModel.isSignUpSuccessful.value) {
                Toast.makeText(
                    context,
                    "Sign up successful",
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
                navController.navigate(Routes.FIRST_SCREEN)
            } else if (signupViewModel.signUpError.value.isNotEmpty()) {
                Toast.makeText(
                    context,
                    signupViewModel.signUpError.value,
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                NormalTextComponent(
                    value = stringResource(id = R.string.hello),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp)
                )
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.firstNameError
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.lastNameError
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )

                CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {
                        PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    },
                    onCheckedChange = {
                        signupViewModel.onEvent(SignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)

                    },
                    isEnabled = signupViewModel.allValidationsPassed.value

                )


                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
//                    PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                    navController.popBackStack()
                    navController.navigate(Routes.LOGIN)
                })
            }

        }

        if (signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }


}

@Preview(device = "id:pixel_6a")
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen(rememberNavController())
}