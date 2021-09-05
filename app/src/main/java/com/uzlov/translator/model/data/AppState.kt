package com.uzlov.translator.model.data

sealed class AppState {

    data class Success(val data: List<WordModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
