package com.uzlov.translator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.datasource.DataSourceLocal
import com.uzlov.translator.model.datasource.DataSourceRemote
import com.uzlov.translator.model.net.AndroidNetworkStatus
import com.uzlov.translator.model.repository.RepositoryImplementation
import com.uzlov.translator.presenter.Interactor
import com.uzlov.translator.utils.parseSearchResults
import com.uzlov.translator.view.main.MainInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) : BaseViewModel() {
    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getData(word: String) {
        compositeDisposable.add(
            interactor.getData(word)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = AppState.Loading(null) }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = parseSearchResults(state)
                liveDataForViewToObserve.value = appState
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }


}