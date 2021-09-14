package com.uzlov.translator.view.main

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.repository.room.HistoryEntity

class MainInteractor(
    private val remoteRepository: com.uzlov.translator.repository.IRepository<List<WordModel>>,
    private val localRepository: com.uzlov.translator.repository.ILocalRepository<List<WordModel>>,
    private val networkStatus: INetworkStatus
) : com.uzlov.translator.core.viewmodel.Interactor<AppState> {

    override suspend fun getData(word: String): AppState {
        val isOnline = networkStatus.observeSateNetwork().value ?: false
        return if (isOnline) {
            localRepository.save(HistoryEntity(word, ""))
            AppState.Success(remoteRepository.getData(word))
        } else {
            AppState.Success(localRepository.getData(word))
        }
    }
}

