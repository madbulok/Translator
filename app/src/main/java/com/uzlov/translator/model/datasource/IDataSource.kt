package com.uzlov.translator.model.datasource

interface IDataSource<T> {
    suspend fun getData(word: String): T
}
