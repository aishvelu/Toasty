package com.example.mealapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green1,
    primaryVariant = Blue1,
    secondary = Peach1,
    secondaryVariant = Peach2,

    )

private val LightColorPalette = lightColors(
    primary = Green1,
    primaryVariant = Blue1,
    secondary = Peach1,
    secondaryVariant = Peach2,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MealAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = ChilankaTypography,
        shapes = Shapes,
        content = content
    )
}