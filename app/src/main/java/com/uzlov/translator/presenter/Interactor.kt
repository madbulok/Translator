package com.uzlov.translator.presenter

interface Interactor<T> {
    suspend fun getData(word: String): T
}