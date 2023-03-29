package com.mikerzdev.snakegame.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikerzdev.snakegame.R
import com.mikerzdev.snakegame.domain.consts.BOARD_COLOR
import com.mikerzdev.snakegame.domain.consts.BOARD_X_CELLS
import com.mikerzdev.snakegame.domain.consts.BOARD_Y_CELLS
import com.mikerzdev.snakegame.domain.model.game.Cell
import com.mikerzdev.snakegame.domain.model.game.GameRenderData
import com.mikerzdev.snakegame.domain.model.game.GameState
import com.mikerzdev.snakegame.presentation.utils.AppFont
import com.mikerzdev.snakegame.presentation.utils.VoidCallback

private fun getCellColor(boardCells: List<Cell>, x: Int, y: Int): Color {
    val colorValue = boardCells.firstOrNull { it.coordinate.x == x && it.coordinate.y == y }?.color
    return if (colorValue != null) Color(colorValue) else Color.Gray
}

@Composable
fun GameBoard(
    renderData: GameRenderData,
    onNewGameClick: VoidCallback,
    onStartGameClick: VoidCallback,
    onStopGameClick: VoidCallback,
) {
    val listX = (0 until BOARD_X_CELLS).toList()
    val listY = (0 until BOARD_Y_CELLS).toList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(BOARD_COLOR))
    ) {
        if (renderData.gameState !is GameState.NotStarted) {
            ScoreView(renderData.gameState.gameData)
        }
        when (renderData.gameState) {
            is GameState.GameOver -> {
                Text(
                    text = buildString {
                        append(renderData.gameState.winnerName)
                        append("\n")
                        append(stringResource(R.string.game_board_winner_copy))
                    },
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = AppFont.eightBitWonder
                )
            }
            is GameState.TieGame -> {
                Text(
                    text = stringResource(R.string.game_board_tie_copy),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
            else -> {
                Box(modifier = Modifier.padding(bottom = 60.dp))
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(10.dp),
        ) {
            itemsIndexed(listY) { _, y ->
                LazyRow {
                    itemsIndexed(listX) { _, x ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(45.dp)
                                .background(
                                    getCellColor(renderData.cellList, x, y),
                                    CircleShape
                                )
                        )
                    }
                }
            }
        }
        GameStateButton(
            renderData = renderData,
            onNewGameClick = onNewGameClick,
            onStartGameClick = onStartGameClick,
            onStopGameClick = onStopGameClick
        )
    }
}

