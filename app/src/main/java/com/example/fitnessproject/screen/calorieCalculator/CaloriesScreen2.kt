package com.example.fitnessproject.screen.calorieCalculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessproject.R
import com.example.fitnessproject.components.ButtonComponent
import com.example.fitnessproject.database.firebase.UserFirebase
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.ui.theme.TDEEBabyBlue
import com.example.fitnessproject.ui.theme.TDEEBlue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun CalorieCalculatorScreen2(calories: Int, bmr: Int, bmi: Float, age: Int, height: Int, weight: Int, checkedOption: String,navController: NavController) {

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 64.dp)
            .fillMaxSize()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "RESULT",
                    fontFamily = FontFamily(Font(R.font.calibri_bold)),
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CalorieInfo(
                        label = "Calories/day", value = calories.toString(), fontColor = Color.White
                    )
                    CalorieInfo(
                        label = "Calories/week",
                        value = (calories * 7).toString(),
                        fontColor = Color.White
                    )
                }
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 360.dp, max = 460.dp)
                    .padding(top = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SmallCard("Gain Weight", "${calories + 500}", "Calories/day")
                        Spacer(modifier = Modifier.width(36.dp))
                        SmallCard("Lose Weight", "${calories - 500}", "Calories/day")
                    }
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SmallCard("BMR", bmr.toString(), "Calories/day")
                        Spacer(modifier = Modifier.width(36.dp))
                        SmallCard("BMI", bmi.toString(), "kg/m²")
                    }
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SmallCard("BWB", BMITable(bmi), "")
                        //Spacer(modifier = Modifier.width(36.dp))
                        //SmallCard("BMR", "1200", "Calories/day")
                    }
                }
            }
        }
                    ButtonComponent("Save data", true,modifier = Modifier
                        .padding(top = 16.dp)
                       ) {
                        UserFirebase().addUserData(
                            Firebase.auth.currentUser!!.uid,
                            age.toInt(),
                            height.toInt(),
                            weight.toInt(),
                            checkedOption.toString(),
                            bmi,
                            bmr,
                            calories,
                            BMITable(bmi)
                        )
                        navController.navigate(Routes.HOME_SCREEN){
                            popUpTo(Routes.HOME_SCREEN){
                                inclusive = true
                            }
                        }

                    }
    }
}


@Composable
fun SmallCard(title: String, value: String, unit: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.size(width = 140.dp, height = 110.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                // maxLines = 1,
                softWrap = true,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = unit,
                fontSize = 10.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
            )
        }
    }
}

@Composable
fun CalorieInfo(label: String, value: String, fontColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = value,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            color = fontColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            color = fontColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )
    }
}

fun BMITable(bmi: Float): String {
    return when {
        bmi >= 1.0 && bmi < 16.0 -> ("Severe Thinness")
        bmi >= 16.0 && bmi < 17.0 -> ("Moderate Thinness")
        bmi >= 17.0 && bmi < 18.5 -> ("Mild Thinness")
        bmi >= 18.5 && bmi < 25.0 -> ("Normal")
        bmi >= 25.0 && bmi < 30.0 -> ("Overweight")
        bmi >= 30.0 && bmi < 35.0 -> ("Obese Class I")
        bmi >= 35.0 && bmi < 40.0 -> ("Obese Class II")
        bmi >= 40.0 -> ("Obese Class III")
        else -> ("Invalid BMI")
    }
}

@Preview(showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun CaloriesScreenPreview() {
//    CalorieCalculatorScreen2(1000, 2121, 422f)
}

