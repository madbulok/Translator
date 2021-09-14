package com.uzlov.translator.repository.datasource

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class BaseInterceptor {

    companion object {

        val interceptor: OkHttpClient
            get() = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
    }
}
