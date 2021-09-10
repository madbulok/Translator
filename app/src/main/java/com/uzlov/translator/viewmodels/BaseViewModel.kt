package com.uzlov.translator.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.translator.model.data.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel() {

    protected val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    protected val vmCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
    )

    abstract fun handleError(throwable: Throwable)

    abstract fun getData(word: String)

    override fun onCleared() {
        cancelAllJob()
    }

    abstract fun cancelAllJob()
}