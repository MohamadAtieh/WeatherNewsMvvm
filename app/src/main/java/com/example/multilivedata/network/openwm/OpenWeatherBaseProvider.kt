package com.example.multilivedata.network.openwm


interface OpenWeatherBaseProvider {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5"
        const val API_KEY = "7eb019c79464406ed0975ebac26806ac"

        const val QUERY_API_KEY = "APPID"
    }

}