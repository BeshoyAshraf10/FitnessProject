package com.example.fitnessproject.caloriecalculator

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessproject.R
import com.example.fitnessproject.ui.theme.TDEEBabyBlue
import com.example.fitnessproject.ui.theme.TDEEBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavController) {

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var checkedOption by remember { mutableStateOf<CheckboxOption?>(null) }
    val options = listOf(
        "Sedentary: little or no exercise",
        "Light: exercise 1-3 times/week",
        "Moderate: exercise 4-5 times/week",
        "Active: daily exercise or intense exercise 3-4 times/week",
        "Very Active: intense exercise 6-7 times/week"
    )
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(options[0]) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .height(660.dp)
                .padding(16.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Calorie Calculator",
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 32.sp
                )

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TDEEBabyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedInputField(
                        label = "Enter your age",
                        value = age,
                        onValueChange = { age = it })

                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TDEEBabyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    OutlinedInputField(
                        label = "Enter your height",
                        value = height,
                        onValueChange = { height = it })

                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TDEEBabyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {

                    OutlinedInputField(
                        label = "Enter your Weight",
                        value = weight,
                        onValueChange = { weight = it })

                }
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Gender",
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Row(verticalAlignment = CenterVertically) {
                        GenderCheckbox(
                            CheckboxOption.Male,
                            checkedOption,
                        ) { checkedOption = it }
                    }
                    Row(verticalAlignment = CenterVertically) {
                        GenderCheckbox(
                            CheckboxOption.Female,
                            checkedOption,
                        ) { checkedOption = it }
                    }
                }

                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Activity Level",
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = !isExpanded }
                    ) {
                        TextField(
                            value = selectedItem,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = !isExpanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            options.forEachIndexed { index, text ->
                                DropdownMenuItem(
                                    text = { Text(text = text) },
                                    onClick = {
                                        selectedItem = options[index]
                                        isExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
                fun calculateTopScreenCalorie(): Int {
                    val result: Int
                    if (checkedOption == CheckboxOption.Male) {
                        result = when (selectedItem) {
                            options[0] -> (calculateBMRForMen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.2).toInt()

                            options[1] -> (calculateBMRForMen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.375).toInt()

                            options[2] -> (calculateBMRForMen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.55).toInt()

                            options[3] -> (calculateBMRForMen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.725).toInt()

                            else -> {
                                (calculateBMRForMen(
                                    weight = weight,
                                    height = height,
                                    age = age
                                ) * 1.9).toInt()
                            }
                        }
                    } else {
                        result = when (selectedItem) {
                            options[0] -> (calculateBMRForWomen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.2).toInt()

                            options[1] -> (calculateBMRForWomen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.375).toInt()

                            options[2] -> (calculateBMRForWomen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.55).toInt()

                            options[3] -> (calculateBMRForWomen(
                                weight = weight,
                                height = height,
                                age = age
                            ) * 1.725).toInt()

                            else -> {
                                (calculateBMRForWomen(
                                    weight = weight,
                                    height = height,
                                    age = age
                                ) * 1.9).toInt()
                            }
                        }
                    }
                    return result
                }

                val context = LocalContext.current

                fun myToast() {
                    val text = "Please fill the missing fields"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }
                Button(
                    onClick = {

                        try {
                            if (checkedOption != null) {
                                val calories = calculateTopScreenCalorie()
                                val bmr = if (checkedOption == CheckboxOption.Male) {
                                    calculateBMRForMen(weight, height, age)
                                } else {
                                    calculateBMRForWomen(weight, height, age)
                                }
                                val bmi = calculateBMI(weight, height)
                                navController.navigate(
                                    "${Routes.SECOND_SCREEN}/$calories/${bmr.toInt()}/${
                                        String.format(
                                            "%.2f",
                                            bmi * 100
                                        ).toFloat()
                                    }"
                                )
                            } else
                                myToast()
                        } catch (e: Exception) {
                            myToast()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TDEEBlue),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Calculate"
                    )

                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun PreviewInfoScreen() {
    InformationScreen(rememberNavController())
}

fun calculateBMRForMen(weight: String, height: String, age: String): Double {
    return (10 * weight.toDouble() + 6.25 * height.toDouble() - 5 * age.toInt() + 5)
}

fun calculateBMRForWomen(weight: String, height: String, age: String): Double {
    return (10 * weight.toDouble() + 6.25 * height.toDouble() - 5 * age.toInt() - 161)
}

fun calculateBMI(weight: String, height: String): Double {
    return weight.toDouble() / ((height.toDouble() * height.toDouble()) / 100)
}

enum class CheckboxOption {
    Male,
    Female
}

@Composable
fun GenderCheckbox(
    option: CheckboxOption,
    checkedOption: CheckboxOption?,
    onCheckedChange: (CheckboxOption?) -> Unit,
) {
    Row(verticalAlignment = CenterVertically) {
        Checkbox(
            checked = checkedOption == option,
            onCheckedChange = { onCheckedChange(if (it) option else null) }
        )
        Text(
            text = if (option == CheckboxOption.Male) "Male" else "Female",
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        )
    }
}


@Composable
fun OutlinedInputField(label: String, value: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        colors = TextFieldDefaults.colors( //test each one
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    )

}
