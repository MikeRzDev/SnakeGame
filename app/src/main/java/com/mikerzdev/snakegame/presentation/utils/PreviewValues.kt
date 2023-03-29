package com.mikerzdev.snakegame.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mikerzdev.snakegame.domain.model.game.Snake
import com.mikerzdev.snakegame.domain.model.game.SnakeMetaData

class SnakePreviewProvider : PreviewParameterProvider<Snake> {
    override val values: Sequence<Snake>
        get() = sequenceOf(
            Snake(metaData = SnakeMetaData(name = "Snake 1", color = Color.Red.value)),
            Snake(metaData = SnakeMetaData(name = "Snake 2",color = Color.Blue.value)),
        )
}