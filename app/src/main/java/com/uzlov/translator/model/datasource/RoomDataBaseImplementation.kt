package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel

class RoomDataBaseImplementation : DataSource<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> {
        return emptyList()
    }
}
