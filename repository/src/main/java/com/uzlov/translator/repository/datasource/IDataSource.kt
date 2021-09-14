package com.uzlov.translator.repository.datasource

interface IDataSource<T> {
    suspend fun getData(word: String): T
}
