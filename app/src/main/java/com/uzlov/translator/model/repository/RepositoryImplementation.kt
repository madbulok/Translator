package com.uzlov.translator.model.repository

import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<WordModel>>) :
    Repository<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> {
        return dataSource.getData(word)
    }
}
