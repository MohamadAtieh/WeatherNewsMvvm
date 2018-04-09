package com.example.multilivedata.network.openwm.search

import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.common.Response.Helper.FETCH_EMPTY
import com.example.multilivedata.network.common.Response.Helper.FETCH_FAILED
import com.example.multilivedata.network.common.Response.Helper.FETCH_SUCCESSFUL
import com.example.multilivedata.network.common.ResponseStatus
import com.google.gson.*
import java.lang.reflect.Type

class OpenWeatherSearchDeserializer : JsonDeserializer<Response<SearchListing>> {

    companion object {
        private const val STATUS_OK = "200"
    }

    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): Response<SearchListing> {
        if (json == null) return buildErrorResponse(FETCH_FAILED)

        val obj = json.asJsonObject
        obj.getAsJsonPrimitive("cod")?.run {
            if (asString != STATUS_OK) return buildErrorResponse(FETCH_FAILED)
        } ?: return buildErrorResponse(FETCH_FAILED)

        val list = obj.getAsJsonArray("list") ?: return buildErrorResponse(FETCH_FAILED)

        return buildSuccessfulResponse(list)
    }

    private fun buildSuccessfulResponse(list: JsonArray): Response<SearchListing> {
        val listing = Gson().fromJson(list, SearchListing::class.java)

        if (listing.list.isEmpty()) return Response(listing, FETCH_EMPTY, null, ResponseStatus.NO_CONTENT)

        return Response(listing, FETCH_SUCCESSFUL, null, ResponseStatus.HAS_CONTENT)
    }

    private fun buildErrorResponse(msg: String?) = Response.hasFailed(msg, null)
}