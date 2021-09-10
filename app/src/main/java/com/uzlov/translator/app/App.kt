package com.uzlov.translator.app

import android.app.Application
import com.uzlov.translator.di.app
import com.uzlov.translator.di.mainScreen
import com.uzlov.translator.di.network
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(app, mainScreen, network))
        }
    }
}