package com.example.multilivedata.ui.weather

import com.example.multilivedata.network.openwm.search.OpenWeatherSearchService
import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {
    fun getSearchApiClient(): OpenWeatherSearchService
    fun getSearchViewModel(): CitySearchViewModel
}