package com.uzlov.translator.model.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.uzlov.translator.model.data.WordModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation : DataSource<List<WordModel>> {

    override suspend fun getData(word: String): List<WordModel> {
        return getService().search(word).await()
    }

    private fun getService(): ApiService {
        return createRetrofit().create(ApiService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(BaseInterceptor.interceptor)
            .build()
    }


    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}
