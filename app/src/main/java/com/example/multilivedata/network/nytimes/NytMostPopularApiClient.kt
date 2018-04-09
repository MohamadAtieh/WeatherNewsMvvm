package com.example.multilivedata.network.nytimes

import com.example.multilivedata.network.BaseApiClient
import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.nytimes.common.CategoryDef
import com.example.multilivedata.network.nytimes.mostpopular.MostPopularListing
import com.example.multilivedata.network.nytimes.mostpopular.NytCustomDeserializer
import com.example.multilivedata.network.nytimes.mostpopular.NytMostPopularProvider
import com.example.multilivedata.network.nytimes.mostpopular.NytMostPopularService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import javax.inject.Inject

class NytMostPopularApiClient @Inject constructor() : BaseApiClient(), NytMostPopularService {
    private val apiClient
        = buildClient(NytMostPopularProvider.BASE_URL).create(NytMostPopularProvider::class.java)

    override fun getGsonAdapters(): Gson
        = GsonBuilder().run {
            registerTypeAdapter(Response::class.java, NytCustomDeserializer())
        create()
        }

    override fun getMostViewed(section: String, timePeriod: String): Call<Response<MostPopularListing>>
        = apiClient.getMostPopularNews(CategoryDef.MOST_VIEWED, section, timePeriod)

    override fun getMostEmailed(section: String, timePeriod: String): Call<Response<MostPopularListing>>
        = apiClient.getMostPopularNews(CategoryDef.MOST_EMAILED, section, timePeriod)

    override fun getMostShared(section: String, timePeriod: String): Call<Response<MostPopularListing>>
        = apiClient.getMostPopularNews(CategoryDef.MOST_SHARED, section, timePeriod)
}