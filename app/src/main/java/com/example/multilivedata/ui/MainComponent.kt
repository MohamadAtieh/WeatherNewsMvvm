package com.example.multilivedata.ui

import android.content.Context
import com.example.multilivedata.AppComponent
import com.example.multilivedata.ui.news.NewsComponent
import com.example.multilivedata.ui.weather.WeatherComponent
import dagger.Component

@ActivityScope
@Component(modules = [MainModule::class], dependencies = [AppComponent::class])
interface MainComponent {
    fun getContext() : Context
    fun getInjector() : MainInjector
    fun getMainViewModel(): MainViewModel

    // have to be declared here
    fun plusWeatherComponent() : WeatherComponent
    fun plusNewsComponent() : NewsComponent
}