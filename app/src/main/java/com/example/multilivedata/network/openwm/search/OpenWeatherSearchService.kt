package com.example.multilivedata.network.openwm.search

import retrofit2.Call
import retrofit2.Response


interface OpenWeatherSearchService {
    fun searchCitiesByName(cityName: String): Call<Response<Object>>
}