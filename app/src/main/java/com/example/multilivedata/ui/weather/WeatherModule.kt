package com.example.multilivedata.ui.weather

import com.example.multilivedata.network.openwm.search.OpenWeatherSearchApiClient
import com.example.multilivedata.network.openwm.search.OpenWeatherSearchService
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {
    @Provides @WeatherScope
    fun provideSearchApiClient(): OpenWeatherSearchService = OpenWeatherSearchApiClient()
}