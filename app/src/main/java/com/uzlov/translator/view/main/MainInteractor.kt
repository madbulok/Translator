package com.uzlov.translator.view.main

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.Repository
import com.uzlov.translator.presenter.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<WordModel>>,
    private val localRepository: Repository<List<WordModel>>,
    private val networkStatus: INetworkStatus
) : Interactor<AppState> {

    override fun getData(word: String): Observable<AppState> {
        val isOnline = networkStatus.isOnlineSingle().blockingGet()
        if (isOnline) {
            return remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            return localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}

