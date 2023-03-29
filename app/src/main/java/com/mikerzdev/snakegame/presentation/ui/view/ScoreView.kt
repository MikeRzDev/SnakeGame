package com.mikerzdev.snakegame.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.domain.model.game.SnakeMetaData
import com.mikerzdev.snakegame.presentation.utils.AppFont


@Composable
fun ScoreView(
    gameData: GameData,
    textColor: Color = Color.White,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SnakeScoreView(snake = gameData.snake1Data, textColor = textColor)
        SnakeScoreView(snake = gameData.snake2Data, textColor = textColor)
    }
}

@Composable
fun SnakeScoreView(snake: SnakeMetaData, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(
                    Color(snake.color),
                    CircleShape
                )
        )
        Text(
            text = "${snake.name}: ${snake.score}",
            modifier = Modifier.padding(10.dp),
            color = textColor,
            fontFamily = AppFont.eightBitWonder,
            fontSize = 12.sp
        )
    }
}