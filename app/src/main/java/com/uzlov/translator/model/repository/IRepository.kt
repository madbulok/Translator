package com.uzlov.translator.model.repository

interface IRepository<T> {
    suspend fun getData(word: String): T
}