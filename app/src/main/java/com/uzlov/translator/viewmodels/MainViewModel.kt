package com.uzlov.translator.viewmodels

import androidx.lifecycle.LiveData
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.repository.parseSearchResults
import com.uzlov.translator.view.main.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : com.uzlov.translator.core.viewmodel.BaseViewModel() {

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun handleError(throwable: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(throwable))
    }

    override fun getData(word: String) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelAllJob()
        vmCoroutineScope.launch { startSearchWord(word) }
    }

    private suspend fun startSearchWord(word: String) = withContext(Dispatchers.IO){
        liveDataForViewToObserve.postValue(parseSearchResults(interactor.getData(word)))
    }

    override fun cancelAllJob() {
        vmCoroutineScope.coroutineContext.cancelChildren()
    }
}