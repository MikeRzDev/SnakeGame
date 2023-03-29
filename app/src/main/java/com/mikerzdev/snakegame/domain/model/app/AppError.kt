package com.mikerzdev.snakegame.domain.model.app

sealed class AppError() {
    object NetworkError: AppError()
    class ServerError(val ErrorCode: Int): AppError()
    class GeneralError(val throwable: Throwable): AppError()
}