package com.uzlov.translator.view.base

import com.uzlov.translator.model.data.AppState

interface View {

    fun renderData(appState: AppState)

}
