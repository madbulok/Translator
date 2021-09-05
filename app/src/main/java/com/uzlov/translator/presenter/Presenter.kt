package com.uzlov.translator.presenter

import com.uzlov.translator.model.data.AppState
import geekbrains.ru.translator.view.base.View

interface Presenter<T : AppState, V : View> {
    fun attachView(view: V)
    fun detachView()
    fun getData(word: String, isOnline: Boolean)
}
