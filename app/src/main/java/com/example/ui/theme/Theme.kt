package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LoopAiDarkColorScheme =
  darkColorScheme(
    primary = BlueLight,
    onPrimary = Color.White,
    primaryContainer = BlueContainerDark,
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = BluePrimary,
    onSecondary = Color.White,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = BlueLight,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkOutline,
    outlineVariant = Color(0xFF1E293B),
  )

private val LoopAiLightColorScheme =
  lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueContainerLight,
    onPrimaryContainer = BlueDark,
    secondary = BlueDark,
    onSecondary = Color.White,
    secondaryContainer = LightSurfaceVariant,
    onSecondaryContainer = BlueDark,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightOutline,
    outlineVariant = Color(0xFFE2E8F0),
  )

@Composable
fun MyApplicationTheme(
  isDarkTheme: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (isDarkTheme) LoopAiDarkColorScheme else LoopAiLightColorScheme,
    typography = Typography,
    content = content
  )
}

