package com.example.multilivedata.network.openwm.forecast

import com.example.multilivedata.network.BaseApiClient
import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class OpenWeatherForecastApiClient @Inject constructor(
) : BaseApiClient(), OpenWeatherForecastService {
    private val apiClient
            = buildClient(BASE_URL).create(OpenWeatherForecastProvider::class.java)

    override fun getGsonAdapters(): Gson
        = GsonBuilder().run {
            registerTypeAdapter(Response::class.java, OpenWeatherForecastDeserializer())
            create()
        }

    override fun getForecastByCityId(cityId: String) = apiClient.getForecastByCityId(cityId)
}