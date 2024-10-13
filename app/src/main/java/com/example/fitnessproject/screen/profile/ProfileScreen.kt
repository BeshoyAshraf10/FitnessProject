package com.example.fitnessproject.screen.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.util.query
import coil.compose.AsyncImage
import com.example.fitnessproject.R
import com.example.fitnessproject.components.BottomNavigationBar
import com.example.fitnessproject.database.firebase.UserData
import com.example.fitnessproject.model.User
import com.example.fitnessproject.navigation.Routes
import com.example.fitnessproject.ui.theme.GradientEnd
import com.example.fitnessproject.ui.theme.GradientStart
import com.example.fitnessproject.ui.theme.Gray
import com.example.fitnessproject.ui.theme.Red
import com.example.fitnessproject.ui.theme.White
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userId = Firebase.auth.currentUser!!.uid
    val user = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(userId) {
        val fetchedUser = withContext(Dispatchers.IO) {
            UserData().getUserData(userId)
        }
        user.value = fetchedUser
    }

    if (user.value != null) {
        // Display user data
        Log.d("debug", "User data: ${user.value}")
        val userdata = user.value!!
        val coroutineScope = rememberCoroutineScope()

        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        var firstName by remember { mutableStateOf(userdata.firstName) }
        var lastName by remember { mutableStateOf(userdata.lastName) }
        var weight by remember { mutableStateOf(userdata.weight.toString()) }
        var height by remember { mutableStateOf(userdata.height.toString()) }
        var goalWeight by remember { mutableStateOf(userdata.goalWeight.toString()) }
        var age by remember { mutableStateOf(userdata.age.toString()) }
        var password by remember { mutableStateOf("") }

        // Edit state variables
        var isEditingFirstName by remember { mutableStateOf(false) }
        var isEditingLastName by remember { mutableStateOf(false) }
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
        var selectedItemIndex by rememberSaveable { mutableStateOf(2) }
        // Replace Column with LazyColumn for scrollability
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = {
                        selectedItemIndex = it
                    },
                    navController = navController
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
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
                            text = "Hello, $firstName",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        IconButton(onClick = {
                            val auth = Firebase.auth
                            auth.signOut()
                            navController.popBackStack()
                            navController.navigate(Routes.LOGIN)
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_logout),
                                contentDescription = "Log out"
                            )
                        }
                    }
                }

                // Editable Rows
                item {
                    EditableRow(
                        label = "Firstname",
                        value = firstName,
                        isEditing = isEditingFirstName,
                        onValueChange = { firstName = it },
                        onEditingChange = { isEditingFirstName = it }
                    )
                }

                item {
                    EditableRow(
                        label = "Lastname",
                        value = lastName,
                        isEditing = isEditingLastName,
                        onValueChange = { lastName = it },
                        onEditingChange = { isEditingLastName = it }
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
                        onValidationSuccess = { weightError = false; weight = it },
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
                        onValidationSuccess = { goalWeightError = false; goalWeight = it },
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
                        onValidationSuccess = { heightError = false; height = it },
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

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            // Launch the coroutine when the button is clicked
                            coroutineScope.launch {
                                updateData(
                                    context = context,
                                    firstName = firstName,
                                    lastName = lastName,
                                    age = age,
                                    height = height,
                                    weight = weight,
                                    goalWeight = goalWeight
                                )
                            }

                        }) {
                            Text(text = "Save changes")
                        }
                    }
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
                                Text(
                                    "500 cal",
                                    color = White
                                ) // You can update this value based on your logic
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Show loading or error message
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
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
                label = { Text(text = "$label $unit") },
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

suspend fun updateData(
    context: Context,
    firstName: String,
    lastName: String,
    age: String,
    height: String,
    weight: String,
    goalWeight: String
) {
    val success = try {
        UserData().updateUserData(
            userId = Firebase.auth.currentUser!!.uid,
            firstName = firstName,
            lastName = lastName,
            age = age.toInt(),
            height = height.toInt(),
            weight = weight.toInt(),
            goalWeight = goalWeight.toInt()
        )
        Log.d("UserData", "Update successful")
        true
    } catch (e: Exception) {
        Log.e("UserData", "Error updating data", e)
        false
    }
    if (success) {
        Toast.makeText(context,"Data updated successfully", Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(context,"Error updating data", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ProfileScreen(rememberNavController())
}