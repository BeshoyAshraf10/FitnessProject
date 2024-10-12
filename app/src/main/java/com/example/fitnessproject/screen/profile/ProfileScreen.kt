package com.example.fitnessproject.screen.profile

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fitnessproject.ui.theme.GradientEnd
import com.example.fitnessproject.ui.theme.GradientStart
import com.example.fitnessproject.ui.theme.Gray
import com.example.fitnessproject.ui.theme.Red
import com.example.fitnessproject.ui.theme.White


@Composable
fun ProfileScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var userName by remember { mutableStateOf("User") }
    var weight by remember { mutableStateOf("70 kg") }
    var goalWeight by remember { mutableStateOf("65 kg") }
    var height by remember { mutableStateOf("170 cm") }
    var age by remember { mutableStateOf("25") }
    var password by remember { mutableStateOf("Password123#") }

    // Edit state variables
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingWeight by remember { mutableStateOf(false) }
    var isEditingGoalWeight by remember { mutableStateOf(false) }
    var isEditingHeight by remember { mutableStateOf(false) }
    var isEditingAge by remember { mutableStateOf(false) }
    var isEditingPassword by remember { mutableStateOf(false) }

    // Error states
    var weightError by remember { mutableStateOf(false) }
    var goalWeightError by remember { mutableStateOf(false) }
    var heightError by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf(false) }

    // Launcher for image selection
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    // Replace Column with LazyColumn for scrollability
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between items
    ) {
        item {
            // Profile Picture and Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Gray)
                        .clickable { launcher.launch("image/*") }
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        FloatingActionButton(
                            onClick = { launcher.launch("image/*") },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Image"
                            )
                        }
                    }
                }

                Text(
                    text = "Hello, $userName",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                IconButton(onClick = { /* TODO: Settings Action */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        }

        // Editable Rows
        item {
            EditableRow(
                label = "Username",
                value = userName,
                isEditing = isEditingName,
                onValueChange = { userName = it },
                onEditingChange = { isEditingName = it }
            )
        }

        item {
            EditableRowWithValidation(
                label = "Weight",
                value = weight,
                isEditing = isEditingWeight,
                onValueChange = { weight = it },
                onEditingChange = { isEditingWeight = it },
                error = weightError,
                validate = { newValue ->
                    val numericValue = newValue.toIntOrNull()
                    numericValue != null && numericValue in 10..200
                },
                onValidationFail = { weightError = true },
                onValidationSuccess = { weightError = false; weight = "$it kg" },
                unit = "kg"
            )
        }

        item {
            EditableRowWithValidation(
                label = "Goal Weight",
                value = goalWeight,
                isEditing = isEditingGoalWeight,
                onValueChange = { goalWeight = it },
                onEditingChange = { isEditingGoalWeight = it },
                error = goalWeightError,
                validate = { newValue ->
                    val numericValue = newValue.toIntOrNull()
                    numericValue != null && numericValue in 10..200
                },
                onValidationFail = { goalWeightError = true },
                onValidationSuccess = { goalWeightError = false; goalWeight = "$it kg" },
                unit = "kg"
            )
        }

        item {
            EditableRowWithValidation(
                label = "Height",
                value = height,
                isEditing = isEditingHeight,
                onValueChange = { height = it },
                onEditingChange = { isEditingHeight = it },
                error = heightError,
                validate = { newValue ->
                    val numericValue = newValue.toIntOrNull()
                    numericValue != null && numericValue in 0..999
                },
                onValidationFail = { heightError = true },
                onValidationSuccess = { heightError = false; height = "$it cm" },
                unit = "cm"
            )
        }

        item {
            EditableRowWithValidation(
                label = "Age",
                value = age,
                isEditing = isEditingAge,
                onValueChange = { age = it },
                onEditingChange = { isEditingAge = it },
                error = ageError,
                validate = { newValue ->
                    val numericValue = newValue.toIntOrNull()
                    numericValue != null && numericValue in 6..100
                },
                onValidationFail = { ageError = true },
                onValidationSuccess = { ageError = false; age = it }
            )
        }

        item {
            EditablePasswordRow(
                label = "Password",
                value = password,
                isEditing = isEditingPassword,
                onValueChange = { password = it },
                onEditingChange = { isEditingPassword = it }
            )
        }

        // Statistics Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Box for Height
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .drawWithCache {
                            val brush = Brush.linearGradient(
                                listOf(
                                    GradientStart, // Use the defined gradient start color
                                    GradientEnd // Use the defined gradient end color
                                )

                            )
                            onDrawBehind {
                                drawRoundRect(
                                    brush,
                                    cornerRadius = CornerRadius(12.dp.toPx())
                                )
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Height", color = White, fontWeight = FontWeight.Bold)
                        Text(height, color = White)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Box for Current Weight
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .drawWithCache {
                            val brush = Brush.linearGradient(
                                listOf(
                                    GradientStart,
                                    GradientEnd
                                )
                            )
                            onDrawBehind {
                                drawRoundRect(
                                    brush,
                                    cornerRadius = CornerRadius(12.dp.toPx())
                                )
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Current Weight", color = White, fontWeight = FontWeight.Bold)
                        Text(weight, color = White)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp)) // Space between rows

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Box for Goal Weight
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .drawWithCache {
                            val brush = Brush.linearGradient(
                                listOf(
                                    GradientStart,
                                    GradientEnd
                                )
                            )
                            onDrawBehind {
                                drawRoundRect(
                                    brush,
                                    cornerRadius = CornerRadius(12.dp.toPx())
                                )
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Goal Weight", color = White, fontWeight = FontWeight.Bold)
                        Text(goalWeight, color = White)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Box for Calories
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .drawWithCache {
                            val brush = Brush.linearGradient(
                                listOf(
                                    GradientStart,
                                    GradientEnd
                                )
                            )
                            onDrawBehind {
                                drawRoundRect(
                                    brush,
                                    cornerRadius = CornerRadius(12.dp.toPx())
                                )
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Calories Burned", color = White, fontWeight = FontWeight.Bold)
                        Text("500 cal", color = White) // You can update this value based on your logic
                    }
                }
            }
        }
    }
}

@Composable
fun EditableRow(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditingChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onEditingChange(false) }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save $label"
                )
            }
        } else {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$label: $value",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                )
                IconButton(onClick = { onEditingChange(true) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $label"
                    )
                }
            }
        }
    }
}

@Composable
fun EditableRowWithValidation(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditingChange: (Boolean) -> Unit,
    error: Boolean,
    validate: (String) -> Boolean,
    onValidationFail: () -> Unit,
    onValidationSuccess: (String) -> Unit,
    unit: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            var inputValue by remember { mutableStateOf(value.replace(" $unit", "")) }

            TextField(
                value = inputValue,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        inputValue = newValue
                    }
                },
                label = { Text(text = label) },
                isError = error,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {
                if (validate(inputValue)) {
                    onValidationSuccess(inputValue)
                    onEditingChange(false)
                } else {
                    onValidationFail()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save $label"
                )
            }
        } else {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$label: $value",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                )
                IconButton(onClick = { onEditingChange(true) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $label"
                    )
                }
            }
        }
    }

    if (error) {
        Text(
            text = "Invalid $label",
            color = Red,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun EditablePasswordRow(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    onEditingChange: (Boolean) -> Unit
) {
    var passwordError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            // Password is visible when editing
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                visualTransformation = VisualTransformation.None, // Show password
                isError = passwordError,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                val validationResult = validatePassword(value)
                if (validationResult.isValid) {
                    onEditingChange(false)
                    passwordError = false
                    errorMessage = ""
                } else {
                    passwordError = true
                    errorMessage = validationResult.errorMessage
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save $label"
                )
            }
        } else {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display dots based on password length when not editing
                val hiddenPassword = "•".repeat(value.length)
                Text(
                    text = "$label: $hiddenPassword",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                )
                IconButton(onClick = { onEditingChange(true) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $label"
                    )
                }
            }
        }
    }

    if (passwordError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

// Result class to hold the validation result
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

// Password validation function
fun validatePassword(password: String): ValidationResult {
    val hasUppercase = password.any { it.isUpperCase() }
    val digitCount = password.count { it.isDigit() }
    val hasSpecialChar = password.any { it in "!@#$%^&*()-_+=<>?" }

    val missingCriteria = mutableListOf<String>()

    if (password.length < 8) {
        missingCriteria.add("at least 8 characters")
    }
    if (!hasUppercase) {
        missingCriteria.add("at least 1 uppercase letter")
    }
    if (digitCount < 3) {
        missingCriteria.add("at least 3 digits")
    }
    if (!hasSpecialChar) {
        missingCriteria.add("at least 1 special character")
    }

    return if (missingCriteria.isEmpty()) {
        ValidationResult(isValid = true)
    } else {
        ValidationResult(
            isValid = false,
            errorMessage = "Password must contain: ${missingCriteria.joinToString(", ")}"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}