package com.uzlov.translator.core.viewmodel

interface Interactor<T> {
    suspend fun getData(word: String): T
}