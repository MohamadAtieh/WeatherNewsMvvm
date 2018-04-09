package com.example.multilivedata.ui.weather

import dagger.Subcomponent

@WeatherScope
@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {
}