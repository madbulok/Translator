package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<WordModel>> {

    override fun getData(word: String): Observable<List<WordModel>> {
        return Observable.empty<List<WordModel>>()
    }
}
