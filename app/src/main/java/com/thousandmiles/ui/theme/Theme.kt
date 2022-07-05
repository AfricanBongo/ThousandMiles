package com.thousandmiles.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Color(0xFF81CA87),
    primaryVariant = Color(0xFF3F7857),
    secondary = Color(0xFF99CDD2),
    secondaryVariant = Color(0xFF58898C),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color(0xFF13251B),
    onSecondary = Color.White,
)

@Composable
fun ThousandMilesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}