package com.example.multilivedata.ui.weather

import com.example.multilivedata.network.openwm.search.OpenWeatherSearchService
import javax.inject.Inject


class CitySearchViewModel @Inject constructor(
        private val apiClient: OpenWeatherSearchService
) {

    fun fetchCityByName(cityQuery: String) {
    }
}