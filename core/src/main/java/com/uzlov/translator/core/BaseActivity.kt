package com.uzlov.translator.core

import androidx.appcompat.app.AppCompatActivity
import com.uzlov.translator.model.data.AppState

abstract class BaseActivity : AppCompatActivity() {
    abstract fun renderData(appState: AppState)
}
