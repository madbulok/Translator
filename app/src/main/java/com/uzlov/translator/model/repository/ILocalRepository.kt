package com.uzlov.translator.model.repository

import com.uzlov.translator.room.HistoryEntity

interface ILocalRepository<T> : IRepository<T> {

    suspend fun save(historyEntity: HistoryEntity)
    suspend fun getAllWord() : T
}
