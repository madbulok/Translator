package com.uzlov.translator.repository

import com.uzlov.translator.repository.datasource.IDataSourceLocal
import com.uzlov.translator.repository.room.HistoryEntity


class LocalRepositoryImpl(private val dataSource: IDataSourceLocal<List<HistoryEntity>>) :
    ILocalRepository<List<HistoryEntity>> {

    override suspend fun save(historyEntity: HistoryEntity) {
        return dataSource.save(historyEntity)
    }

    override suspend fun getData(word: String): List<HistoryEntity> {
        return dataSource.getData(word)
    }

    override suspend fun getAllWord(): List<HistoryEntity> {
        return dataSource.getAllSavedWord()
    }
}