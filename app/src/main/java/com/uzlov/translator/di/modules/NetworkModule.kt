package com.uzlov.translator.di.modules

import android.app.Application
import android.content.Context
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.net.INetworkStatus
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    internal fun getNetworkModule(app: Application) : INetworkStatus = AndroidNetworkStatus(app)

}