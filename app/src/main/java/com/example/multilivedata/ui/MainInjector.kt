package com.example.multilivedata.ui

import com.example.multilivedata.ui.news.NewsComponent
import com.example.multilivedata.ui.weather.WeatherComponent
import javax.inject.Inject

@ActivityScope
class MainInjector @Inject constructor(private val component: MainComponent) {
    private var weatherComponent: WeatherComponent? = null
    private var newsComponent: NewsComponent? = null

    fun plusWeatherComponent()
        = weatherComponent ?: component.plusWeatherComponent().also { weatherComponent = it }

    fun clearWeatherComponent() { weatherComponent = null }

    fun plusNewsComponent()
        = newsComponent ?: component.plusNewsComponent().also { newsComponent = it }

    fun clearNewsComponent() { newsComponent = null }
}