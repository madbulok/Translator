package com.uzlov.translator.view.main

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.IDataSourceLocal
import com.uzlov.translator.model.net.INetworkStatus
import com.uzlov.translator.model.repository.ILocalRepository
import com.uzlov.translator.model.repository.IRepository
import com.uzlov.translator.presenter.Interactor
import com.uzlov.translator.room.HistoryEntity

class MainInteractor(
    private val remoteRepository: IRepository<List<WordModel>>,
    private val localRepository: ILocalRepository<List<WordModel>>,
    private val networkStatus: INetworkStatus
) : Interactor<AppState> {

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

