package com.example.multilivedata.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseApiClient {
    private val clientBuilder: Retrofit.Builder
        = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(getGsonAdapters()))
        }

    abstract fun getGsonAdapters(): Gson

    fun buildClient(baseUrl: String): Retrofit = clientBuilder.baseUrl(baseUrl).build()
}