package com.uzlov.translator.model.repository

import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.IDataSource

class RemoteRepositoryImplementation(private val dataSource: IDataSource<List<WordModel>>) :
    IRepository<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> {
        return dataSource.getData(word)
    }
}
