package com.uzlov.translator.view.main

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.Repository
import com.uzlov.translator.presenter.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<WordModel>>,
    private val localRepository: Repository<List<WordModel>>,
    private val networkStatus: INetworkStatus
) : Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        val isOnline = networkStatus.observeSateNetwork().value ?: false
        return if (isOnline) {
            AppState.Success(remoteRepository.getData(word))
        } else {
            AppState.Success(localRepository.getData(word))
        }
    }
}

