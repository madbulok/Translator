package com.uzlov.translator.view.main

import com.uzlov.translator.model.repository.ILocalRepository
import com.uzlov.translator.presenter.IHistoryInteractor
import com.uzlov.translator.room.HistoryEntity

class HistoryInteractorImpl(private val localRepository: ILocalRepository<List<HistoryEntity>>) : IHistoryInteractor<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> {
        return localRepository.getData(word)
    }

    override suspend fun save(historyEntity: HistoryEntity) {
        localRepository.save(historyEntity)
    }

    override suspend fun getAllSavedWord(): List<HistoryEntity> {
        return localRepository.getAllWord()
    }
}