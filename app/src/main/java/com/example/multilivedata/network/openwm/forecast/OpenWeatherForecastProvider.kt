package com.example.multilivedata.network.openwm.forecast

import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.API_KEY
import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.QUERY_API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherForecastProvider {

    companion object {
        private const val PATH_FORECAST = "forecast"
        private const val QUERY_ID = "id"
    }

    @GET(PATH_FORECAST)
    fun getForecastByCityId(
            @Query(QUERY_ID) cityId: String,
            @Query(QUERY_API_KEY) apiKey: String = API_KEY
    ) : Call<Response<Object>>

}