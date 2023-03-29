package com.mikerzdev.snakegame.presentation.ui.screen.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.domain.model.game.SnakeMetaData
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import com.mikerzdev.snakegame.presentation.utils.VoidCallback
import com.mikerzdev.snakegame.presentation.utils.argbColorToULongColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    val snake1MetaDataLiveData = MutableLiveData<SnakeMetaData>()
    val snake2MetaDataLiveData = MutableLiveData<SnakeMetaData>()

    private var gameData = GameData.empty()

    fun getGameSettings() {
        viewModelScope.launch {
            localRepository.getGameSettings()?.let {
                gameData = it
                snake1MetaDataLiveData.postValue(it.snake1Data)
                snake2MetaDataLiveData.postValue(it.snake2Data)
            }
        }
    }

    fun setPlayer1Name(name: String) {
        gameData.snake1Data.name = name
    }

    fun setPlayer2Name(name: String) {
        gameData.snake2Data.name = name
    }

    fun setPlayer1Color(color: Int) {
        gameData.snake1Data.color = argbColorToULongColor(color)
    }

    fun setPlayer2Color(color: Int) {
        gameData.snake2Data.color = argbColorToULongColor(color)
    }

    fun saveGameSettings(callback: VoidCallback) {
        viewModelScope.launch {
            localRepository.saveGameSettings(gameData)
        }.invokeOnCompletion {
            callback()
        }
    }
}