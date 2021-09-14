package com.uzlov.translator.repository

interface IRepository<T> {
    suspend fun getData(word: String): T
}