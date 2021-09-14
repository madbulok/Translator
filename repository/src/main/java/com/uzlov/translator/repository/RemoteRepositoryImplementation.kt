package com.uzlov.translator.repository

import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.repository.datasource.IDataSource

class RemoteRepositoryImplementation(private val dataSource: IDataSource<List<WordModel>>) :
    IRepository<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> {
        return dataSource.getData(word)
    }
}
