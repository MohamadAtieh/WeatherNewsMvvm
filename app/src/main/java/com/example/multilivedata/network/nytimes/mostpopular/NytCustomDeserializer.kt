package com.example.multilivedata.network.nytimes.mostpopular

import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.common.ResponseStatus
import com.google.gson.*
import java.lang.reflect.Type

class NytCustomDeserializer : JsonDeserializer<Response<MostPopularListing>> {

    companion object {
        const val STATUS_ERROR = "ERROR"
        const val STATUS_OK = "OK"

        const val FETCH_SUCCESSFUL = "Result Successful"
        const val FETCH_FAILED = "Failed to Fetch"
        const val FETCH_EMPTY = "No results found"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Response<MostPopularListing> {
        if (json == null) {
            return buildErrorResponse(null)
        }

        val obj = json.asJsonObject

        val status = obj.getAsJsonPrimitive("status") ?: return buildErrorResponse(null)

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

        return buildErrorResponse(null)
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

        return buildErrorResponse(null)
    }

    private fun buildErrorResponse(msg: String?): Response<MostPopularListing> {
        return Response(
                null,
                FETCH_FAILED,
                if (msg == null || msg.isBlank()) Exception() else Exception(msg),
                ResponseStatus.ERROR)
    }
}