package com.mikerzdev.snakegame.presentation.ui.view

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.mikerzdev.snakegame.R
import com.mikerzdev.snakegame.domain.model.game.GameRenderData
import com.mikerzdev.snakegame.domain.model.game.GameState
import com.mikerzdev.snakegame.presentation.utils.AppColor
import com.mikerzdev.snakegame.presentation.utils.AppFont
import com.mikerzdev.snakegame.presentation.utils.VoidCallback

@Composable
fun GameStateButton(
    renderData: GameRenderData,
    onNewGameClick: VoidCallback,
    onStartGameClick: VoidCallback,
    onStopGameClick: VoidCallback,
) {
    lateinit var onClick: () -> Unit
    lateinit var text: String
    val color = ButtonDefaults.buttonColors(
        backgroundColor = AppColor.mintGreen,
        contentColor = Color.Black
    )
    when (renderData.gameState) {
        is GameState.NotStarted -> {
            onClick = onNewGameClick
            text = stringResource(R.string.game_state_button_new_game)
        }
        is GameState.StartGame -> {
            onClick = onStartGameClick
            text = stringResource(R.string.game_state_button_start_game)
        }
        is GameState.InProgress -> {
            onClick = onStopGameClick
            text = stringResource(R.string.game_state_button_stop_game)
        }
        is GameState.GameOver -> {
            onClick = onStopGameClick
            text = stringResource(R.string.game_state_button_stop_game)
        }
        is GameState.TieGame -> {
            onClick = onStartGameClick
            text = stringResource(R.string.game_state_button_stop_game)
        }
    }

    Button(
        onClick = onClick,
        colors = color
    ) {
        Text(text = text, fontSize = 20.sp, fontFamily = AppFont.eightBitWonder)
    }
}