package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel


class IDataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    IDataSource<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> = remoteProvider.getData(word)
}