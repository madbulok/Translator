package com.uzlov.translator.presenter

import io.reactivex.Observable

interface Interactor<T> {

    fun getData(word: String): Observable<T>
}