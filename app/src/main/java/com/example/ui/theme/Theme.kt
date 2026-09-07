package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NetRed,
    onPrimary = Color.White,
    primaryContainer = NetRedDark,
    onPrimaryContainer = Color.White,
    secondary = NetBlue,
    onSecondary = Color.White,
    secondaryContainer = NetDarkSurfaceVariant,
    onSecondaryContainer = Color.White,
    tertiary = NetAmber,
    background = NetDarkBackground,
    onBackground = NetTextPrimary,
    surface = NetDarkSurface,
    onSurface = NetTextPrimary,
    surfaceVariant = NetDarkSurfaceVariant,
    onSurfaceVariant = NetTextSecondary,
    outline = NetBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
