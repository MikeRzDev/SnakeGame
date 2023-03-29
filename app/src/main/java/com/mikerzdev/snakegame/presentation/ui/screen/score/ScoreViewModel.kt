package com.mikerzdev.snakegame.presentation.ui.screen.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    val scoreLiveData = MutableLiveData<List<GameData>>()

    fun getScoreList() {
        viewModelScope.launch {
            scoreLiveData.postValue(localRepository.getScoreList())
        }
    }
}