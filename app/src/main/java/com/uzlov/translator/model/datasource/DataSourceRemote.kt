package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel


class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> = remoteProvider.getData(word)
}