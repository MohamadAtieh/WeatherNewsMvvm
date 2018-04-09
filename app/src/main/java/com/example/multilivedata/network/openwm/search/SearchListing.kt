package com.example.multilivedata.network.openwm.search

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)

data class SearchEntity(
        @SerializedName("id") val cityId: Int,
        @SerializedName("name") val cityName: String,
        val weather: List<WeatherEntity>
)

data class SearchListing(
        val list: List<SearchEntity>
)
