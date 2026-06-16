package com.example.myhabitkit.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DraculaColorScheme = darkColorScheme(
    primary = DraculaPurple,
    onPrimary = DraculaBackground,
    secondary = DraculaPink,
    onSecondary = DraculaBackground,
    tertiary = DraculaCyan,
    onTertiary = DraculaBackground,
    background = DraculaBackground,
    onBackground = DraculaForeground,
    surface = DraculaSurface,
    onSurface = DraculaForeground,
    error = DraculaRed,
    onError = DraculaForeground
)

@Composable
fun MyHabitKitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We disable dynamic color by default to enforce Dracula theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            else -> DraculaColorScheme // Always use Dracula for both light and dark for now, or just default to it
        }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
