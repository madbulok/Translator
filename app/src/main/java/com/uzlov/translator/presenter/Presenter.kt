package com.uzlov.translator.presenter

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.view.base.View

interface Presenter<T : AppState, V : View> {
    fun attachView(view: V)
    fun detachView()
    fun getData(word: String)
}
