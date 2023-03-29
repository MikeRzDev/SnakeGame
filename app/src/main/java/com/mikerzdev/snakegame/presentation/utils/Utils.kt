package com.mikerzdev.snakegame.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun dimColor(colorValue: ULong): ULong {
    val color = Color(colorValue)
    return Color(
        red = color.red * 0.5f,
        green = color.green * 0.5f,
        blue = color.blue * 0.5f
    ).value
}

fun argbColorToULongColor(color: Int): ULong {
    return Color(color).value
}

fun uLongColorToArgbColor(color: ULong): Int {
    return Color(color).toArgb()
}