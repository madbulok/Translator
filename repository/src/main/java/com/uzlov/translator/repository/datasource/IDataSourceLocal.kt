package com.uzlov.translator.repository.datasource

import com.uzlov.translator.repository.room.HistoryEntity

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun save(historyEntity: HistoryEntity)
    suspend fun getAllSavedWord() : List<HistoryEntity>
}