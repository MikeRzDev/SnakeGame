package com.mikerzdev.snakegame.presentation.ui.screen.score

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikerzdev.snakegame.R
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.presentation.ui.view.ScoreView
import com.mikerzdev.snakegame.presentation.utils.AppColor
import com.mikerzdev.snakegame.presentation.utils.AppFont
import com.mikerzdev.snakegame.presentation.utils.VoidCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : ComponentActivity() {

    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getScoreList()
        setContent {
            val state = viewModel.scoreLiveData.observeAsState()
            if (state.value?.isEmpty() == false) {
                state.value?.let { ScoreList(it, onBackPressed = { finish() }) }
            } else {
                EmptyScoreView(onBackPressed = { finish() })
            }
        }
    }
}

@Composable
fun EmptyScoreView(onBackPressed: VoidCallback) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColor.mintGreen)
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
        }
        Text(
            text = stringResource(R.string.score_text_no_scores), fontFamily = AppFont.eightBitWonder, modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}

@Composable
fun ScoreList(scoreList: List<GameData>, onBackPressed: VoidCallback) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .background(color = AppColor.mintGreen)
    ) {
        IconButton(onClick = onBackPressed, modifier = Modifier.align(Alignment.Start)) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
        }
        Text(
            text = stringResource(R.string.score_screen_title),
            fontFamily = AppFont.eightBitWonder,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 20.dp),
            color = AppColor.darkGreen
        )
        LazyColumn {
            itemsIndexed(scoreList) { _, gameData ->
                ScoreView(gameData = gameData, textColor = AppColor.darkGreen)
            }
        }
    }
}
