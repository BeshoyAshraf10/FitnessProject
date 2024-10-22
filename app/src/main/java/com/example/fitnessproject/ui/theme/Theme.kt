package com.example.fitnessproject.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ThemeColors.Night.primary,
    secondary = ThemeColors.Night.secondary,
    onPrimary = Color.White,
    surface = ThemeColors.Night.surface,
    background = ThemeColors.Night.background,
    onSurface = ThemeColors.Night.text,
    onBackground = ThemeColors.Night.text,
    surfaceContainer = Color(0xFF26262B)
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeColors.Day.primary,
    secondary = ThemeColors.Day.secondary,
    onPrimary = Color.White,
    surface = ThemeColors.Day.surface,
    background = ThemeColors.Day.background,
    onSurface = ThemeColors.Day.text,
    onBackground = ThemeColors.Day.text,
    surfaceContainer = Color(0xFFC9CBE0)
)


@Composable
fun FitnessProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val colors = if (!darkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,

        content = content,
    )
}

private val DarkColors = darkColorScheme(
    primary = Color(0xFF42A5F5),
    onPrimary = Color.Black,
    // Add other color definitions as needed
)
private val LightColors = lightColorScheme(
    primary = Color(0xFF42A5F5),
    onPrimary = Color.White,
    // Add other color definitions as needed
)
@Composable
fun StepsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // Ensure Typography is defined
        content = content
    )
}