package com.uzlov.translator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(

    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {
    protected val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    open fun getData(word: String, isOnline: Boolean): LiveData<AppState> = liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
    }
}