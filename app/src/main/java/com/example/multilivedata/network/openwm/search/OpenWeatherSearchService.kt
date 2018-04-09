package com.example.multilivedata.network.openwm.search

import com.example.multilivedata.network.common.Response
import retrofit2.Call

interface OpenWeatherSearchService {
    fun searchCitiesByName(cityName: String): Call<Response<SearchListing>>
}