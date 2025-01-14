package com.interview.promova_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = primary,
    secondary = secondary,
    background = surface
)


private val DarkColorPalette = lightColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    background = surfaceDark
)

@Composable
fun PromovaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = PromovaTypography,
        content = content
    )
}