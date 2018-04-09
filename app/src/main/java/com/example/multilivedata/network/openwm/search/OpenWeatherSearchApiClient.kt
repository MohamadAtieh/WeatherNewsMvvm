package com.example.multilivedata.network.openwm.search

import com.example.multilivedata.network.BaseApiClient
import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.openwm.OpenWeatherBaseProvider.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class OpenWeatherSearchApiClient @Inject constructor(
) : BaseApiClient(), OpenWeatherSearchService {

    private val apiClient
        = buildClient(BASE_URL).create(OpenWeatherSearchProvider::class.java)

    override fun getGsonAdapters(): Gson
        = GsonBuilder().run {
            registerTypeAdapter(Response::class.java, OpenWeatherSearchDeserializer())
            create()
        }

    override fun searchCitiesByName(cityName: String) = apiClient.searchCities(cityName)
}