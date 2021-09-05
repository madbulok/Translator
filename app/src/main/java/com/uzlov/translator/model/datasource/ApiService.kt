package com.uzlov.translator.model.datasource

import com.uzlov.translator.model.data.WordModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<WordModel>>
}
