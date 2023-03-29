package com.mikerzdev.snakegame.presentation.ui.screen.game

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mikerzdev.snakegame.R
import com.mikerzdev.snakegame.domain.model.game.GameRenderData
import com.mikerzdev.snakegame.domain.model.game.SoundEffect
import com.mikerzdev.snakegame.presentation.ui.view.GameBoard
import com.mikerzdev.snakegame.presentation.utils.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SnakeGameActivity : ComponentActivity() {

    private val viewModel: SnakeGameViewModel by viewModels()
    private lateinit var soundEffectsMap: Map<SoundEffect, MediaPlayer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSoundEffects()

        viewModel.gameSoundLiveData.bind(this) { soundEffect ->
            soundEffectsMap[soundEffect]?.start()
        }

        setContent {
            val state = viewModel.gameGraphicsLiveData.observeAsState()
            Column(modifier = Modifier.background(Color.Black)) {
                IconButton(onClick = { finish() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }
                GameBoard(
                    state.value ?: GameRenderData.empty(),
                    onNewGameClick = { viewModel.resetGame() },
                    onStartGameClick = { viewModel.startGame() },
                    onStopGameClick = { viewModel.stopGame() })
            }
        }
    }

    private fun initSoundEffects() {
        soundEffectsMap = mapOf(
            SoundEffect.SNAKE_1_MOVE to MediaPlayer.create(this, R.raw.audio_snake_move_1),
            SoundEffect.SNAKE_2_MOVE to MediaPlayer.create(this, R.raw.audio_snake_move_2),
            SoundEffect.WINNER to MediaPlayer.create(this, R.raw.audio_winner),
        )
    }

}