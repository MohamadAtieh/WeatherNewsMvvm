package com.example.multilivedata.network.openwm.forecast

import retrofit2.Call
import retrofit2.Response

interface OpenWeatherForecastService {
    fun getForecastByCityId(cityId: String): Call<Response<Object>>
}