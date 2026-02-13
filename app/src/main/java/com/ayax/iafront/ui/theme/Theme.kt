package com.ayax.iafront.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Light palette used when the device is in light mode.
private val LightColors = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = SurfaceLight
)

// Dark palette used when the device is in dark mode.
private val DarkColors = darkColorScheme(
    primary = BlueSecondary,
    secondary = BluePrimary
)

@Composable
fun AIFrontTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Keep theme selection aligned with the system appearance unless overridden.
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
