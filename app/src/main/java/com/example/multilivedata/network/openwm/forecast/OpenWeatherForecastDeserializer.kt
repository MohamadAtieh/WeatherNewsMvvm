package com.example.multilivedata.network.openwm.forecast

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class OpenWeatherForecastDeserializer : JsonDeserializer<Object> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Object {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}