package com.example.fitnessproject.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TDEEBlue =Color(0XFF1367c1)
val TDEEGray =Color(0XFF98a2ac)
val TDEEBabyBlue=Color(0XFFf3f8fc)

val Primary = Color(0xFF92A3FD)
val Secondary = Color(0xFF9DCEFF)
val TextColor = Color(0xFF1D1617)
val AccentColor = Color(0xFFC58BF2)
val GrayColor = Color(0xFF7B6F72)
val WhiteColor = Color(0xFFFFFFFF)
val BgColor = Color(0xFFF7F8F8)


val Gray = Color(0xFFB8B8B8) // Default gray color for profile picture background
val GradientStart = Color(0xFF9E82F0) // Start color for gradient boxes
val GradientEnd = Color(0xFF42A5F5) // End color for gradient boxes
val White = Color(0xFFFFFFFF) // White color
val Red = Color(0xFFFF0000) // Red color for error messages
val DarkGray = Color(0xFF4A4A4A) // Optional: Dark gray for text or backgrounds


val blueLight = Color(0xFFBBDEFB) // Light blue
val blueDark = Color(0xFF0D47A1)  // Dark blue
val grayLight = Color(0xFFEEEEEE)  // Light gray
val grayDark = Color(0xFF424242)   // Dark gray
val whiteText = Color(0xFFFFFFFF)      // White color
val accentBlue = Color(0xFF2196F3) // Accent blue
val errorColor = Color(0xFFF44336) // Error color (red)

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color, // Added secondary color
    val error: Color,     // Added error color
    val accent: Color,    // Added accent color
    val text: Color
) {
    data object Night : ThemeColors(
        background = grayLight,  // Light gray background
        surface = whiteText,         // White surface for cards/buttons
        primary = blueDark,      // Dark blue as primary color
        secondary = GradientStart,    // Light blue as secondary color
        error = errorColor,      // Error color
        accent = accentBlue,      // Accent blue color
        text = Color.Black        // Black text for readability
    )

    data object Day : ThemeColors(
        background = grayDark,    // Dark gray background
        surface = grayLight,      // Light gray surface for cards/buttons
        primary = blueDark,       // Dark blue as primary color
        secondary = GradientStart,     // Light blue as secondary color
        error = errorColor,       // Error color
        accent = accentBlue,       // Accent blue color
        text = Color.White         // White text for contrast
    )
}



