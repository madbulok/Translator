package com.uzlov.translator.model.datasource

import com.uzlov.translator.room.HistoryEntity

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun save(historyEntity: HistoryEntity)
    suspend fun getAllSavedWord() : List<HistoryEntity>
}