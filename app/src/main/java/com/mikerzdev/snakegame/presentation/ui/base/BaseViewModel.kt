package com.mikerzdev.snakegame.presentation.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikerzdev.snakegame.domain.model.app.AppError

abstract class BaseViewModel : ViewModel() {
    val appError =  MutableLiveData<AppError>()
    val showProgress = MutableLiveData<Boolean>()
}