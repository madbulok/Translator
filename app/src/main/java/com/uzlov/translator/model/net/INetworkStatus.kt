package com.uzlov.translator.model.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


interface INetworkStatus {
    fun observeSateNetwork(): LiveData<Boolean>
    fun removeObserve(owner: LifecycleOwner)
}