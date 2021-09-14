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
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribe({ state ->
                    appState = parseSearchResults(state)
                    liveDataForViewToObserve.value = appState
                }, {
                    liveDataForViewToObserve.value = AppState.Error(it)
                })
        )
    }
}