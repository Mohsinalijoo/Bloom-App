package com.example.bloom_app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BloomColorScheme = lightColorScheme(
    primary = BloomPrimary,
    onPrimary = BloomSurface,
    primaryContainer = BloomLightPink,
    onPrimaryContainer = BloomPrimaryDark,
    secondary = BloomSecondary,
    onSecondary = BloomSurface,
    secondaryContainer = BloomCream,
    onSecondaryContainer = BloomTextDark,
    background = BloomBackground,
    onBackground = BloomTextDark,
    surface = BloomSurface,
    onSurface = BloomOnSurface,
    surfaceVariant = BloomCardBackground,
    onSurfaceVariant = BloomTextLight,
    outline = BloomDivider,
    outlineVariant = BloomIndicatorInactive
)

@Composable
fun BloomTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BloomBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = BloomColorScheme,
        typography = BloomTypography,
        content = content
    )
}