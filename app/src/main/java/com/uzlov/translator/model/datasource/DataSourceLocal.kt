package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> = remoteProvider.getData(word)
}
