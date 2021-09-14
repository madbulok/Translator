package com.uzlov.translator.repository

import com.uzlov.translator.repository.room.HistoryEntity


interface ILocalRepository<T> : IRepository<T> {

    suspend fun save(historyEntity: HistoryEntity)
    suspend fun getAllWord() : T
}
