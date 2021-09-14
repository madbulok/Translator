package com.uzlov.translator.model.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AndroidNetworkStatus(context: Context?) : INetworkStatus {
    private val statusSubject: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        statusSubject.postValue(false)
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    statusSubject.postValue(true)
                }

                override fun onUnavailable() {
                    statusSubject.postValue(false)
                }

                override fun onLost(network: Network) {
                    statusSubject.postValue(false)
                }
            })
    }

    override fun observeSateNetwork(): LiveData<Boolean> = statusSubject

    override fun removeObserve(owner: LifecycleOwner) {
        statusSubject.removeObservers(owner)
    }


}
