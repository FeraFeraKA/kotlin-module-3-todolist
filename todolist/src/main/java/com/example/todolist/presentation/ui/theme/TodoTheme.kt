package com.example.todolist.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF155F54),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA8EFE0),
    onPrimaryContainer = Color(0xFF00201A),
    secondaryContainer = Color(0xFFCDE8E1),
    onSecondaryContainer = Color(0xFF06201B),
    surface = Color(0xFFF5FBF8),
    background = Color(0xFFF5FBF8),
    onSurface = Color(0xFF18221F),
    onBackground = Color(0xFF18221F),
    surfaceContainer = Color(0xFFE5F2ED),
    onSurfaceVariant = Color(0xFF42544E),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8CD3C4),
    onPrimary = Color(0xFF00382F),
    secondaryContainer = Color(0xFF2F4B44),
    onSecondaryContainer = Color(0xFFCDE8E1),
    surface = Color(0xFF101E1B),
    background = Color(0xFF101E1B),
    onSurface = Color(0xFFE1EEE9),
    onBackground = Color(0xFFE1EEE9),
    surfaceContainer = Color(0xFF21322D),
    onSurfaceVariant = Color(0xFFBACFC8),
)

@Composable
fun TodoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content,
    )
}
