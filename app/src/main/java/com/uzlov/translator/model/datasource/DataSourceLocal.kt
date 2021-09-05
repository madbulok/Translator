package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<WordModel>> {

    override fun getData(word: String): Observable<List<WordModel>> = remoteProvider.getData(word)
}
