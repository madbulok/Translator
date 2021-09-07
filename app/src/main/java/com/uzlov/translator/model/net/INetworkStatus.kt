package com.uzlov.translator.model.net

import io.reactivex.Single

interface INetworkStatus {
    fun isOnlineSingle(): Single<Boolean>
}