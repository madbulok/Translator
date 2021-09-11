package com.uzlov.translator.viewmodels

import androidx.lifecycle.LiveData
import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.presenter.IHistoryInteractor
import com.uzlov.translator.room.HistoryEntity
import com.uzlov.translator.utils.mapHistoryEntityToSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val historyInteractor: IHistoryInteractor<List<HistoryEntity>>) :
    BaseViewModel() {


    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun handleError(throwable: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(throwable))
    }

    override fun getData(word: String) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelAllJob()
        vmCoroutineScope.launch {
            startLoadingHistoryWord(word)
        }
    }

    fun getAllHistoryWords() {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelAllJob()
        vmCoroutineScope.launch {
            startLoadingAllHistoryWord()
        }
    }

    private suspend fun startLoadingHistoryWord(word: String) = withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(
            AppState.Success(
                mapHistoryEntityToSearchResult(historyInteractor.getData(word))
            )
        )
    }

    private suspend fun startLoadingAllHistoryWord() = withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(
            AppState.Success(
                mapHistoryEntityToSearchResult(
                    historyInteractor.getAllSavedWord()
                )
            )
        )
    }

    override fun cancelAllJob() {
        vmCoroutineScope.coroutineContext.cancelChildren()
    }
}