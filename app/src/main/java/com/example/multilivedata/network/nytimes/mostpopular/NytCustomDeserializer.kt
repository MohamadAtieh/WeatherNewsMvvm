package com.example.multilivedata.network.nytimes.mostpopular

import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.common.Response.Helper.FETCH_EMPTY
import com.example.multilivedata.network.common.Response.Helper.FETCH_FAILED
import com.example.multilivedata.network.common.Response.Helper.FETCH_SUCCESSFUL
import com.example.multilivedata.network.common.ResponseStatus
import com.google.gson.*
import java.lang.reflect.Type

class NytCustomDeserializer : JsonDeserializer<Response<MostPopularListing>> {

    companion object {
        const val STATUS_ERROR = "ERROR"
        const val STATUS_OK = "OK"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Response<MostPopularListing> {
        if (json == null) {
            return buildErrorResponse(FETCH_FAILED)
        }

        val obj = json.asJsonObject

        val status = obj.getAsJsonPrimitive("status") ?: return buildErrorResponse(FETCH_FAILED)

        if (status.asString == STATUS_ERROR) {
            val errorList = obj.getAsJsonArray("errors")
            if (errorList != null) {
                return buildErrorResponse(errorList)
            }
        } else if (status.asString == STATUS_OK) {
            val results = obj.getAsJsonArray("results")
            if (results != null) {
                return buildSuccessfulResponse(obj)
            }
        }

        return buildErrorResponse(FETCH_FAILED)
    }

    private fun buildSuccessfulResponse(result: JsonObject): Response<MostPopularListing> {
        val listing = Gson().fromJson(result, MostPopularListing::class.java)

        if (listing.results.isEmpty()) {
            return Response(listing, FETCH_EMPTY, null, ResponseStatus.NO_CONTENT)
        }

       return Response(listing, FETCH_SUCCESSFUL, null, ResponseStatus.HAS_CONTENT)
    }

    private fun buildErrorResponse(errorList: JsonArray): Response<MostPopularListing> {
        val err = errorList[0]
        if (err.isJsonPrimitive) {
            val errorMessage = (err as JsonPrimitive).run {
                if (isString) asString else null
            }

            return buildErrorResponse(errorMessage)
        }

        return buildErrorResponse(FETCH_FAILED)
    }

    private fun buildErrorResponse(msg: String?) = Response.hasFailed(msg, null)

}