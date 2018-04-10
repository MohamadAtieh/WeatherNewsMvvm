package com.example.multilivedata.network.openwm.search

import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.API_KEY
import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.QUERY_API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherSearchProvider {

    companion object {
        private const val PATH_FIND = "find"
        private const val QUERY_SEARCH_TYPE = "type"
        private const val QUERY_Q = "q"

        private const val QUERY_SEARCH_TYPE_DEFAULT_VALUE = "like"
    }

    @GET(PATH_FIND)
    fun searchCities(
            @Query(QUERY_Q) cityName: String,
            @Query(QUERY_SEARCH_TYPE) searchType: String = QUERY_SEARCH_TYPE_DEFAULT_VALUE,
            @Query(QUERY_API_KEY) apiKey: String = API_KEY
    ) : Call<Response<Object>>

}