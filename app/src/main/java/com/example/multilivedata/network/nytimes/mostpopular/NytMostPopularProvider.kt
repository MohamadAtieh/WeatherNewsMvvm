package com.example.multilivedata.network.nytimes.mostpopular

import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.nytimes.common.Category
import com.example.multilivedata.network.nytimes.common.Section
import com.example.multilivedata.network.nytimes.common.TimePeriod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NytMostPopularProvider {

    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2"
        const val API_KEY = "53827735437849d2ba01cd304c9e8982"

        private const val PATH_CATEGORY = "category"
        private const val PATH_SECTION = "section"
        private const val PATH_TIME_PERIOD = "timePeriod"
        private const val QUERY_API_KEY = "api-key"
    }

    @GET("/{$PATH_CATEGORY}/{$PATH_SECTION}/{$PATH_TIME_PERIOD}")
    fun getMostPopularNews(
            @Path(PATH_CATEGORY) @Category category: String,
            @Path(PATH_SECTION) @Section section: String,
            @Path(PATH_TIME_PERIOD) @TimePeriod timePeriod: String,
            @Query(QUERY_API_KEY) apiKey: String = API_KEY
    ) : Call<Response<MostPopularListing>>
}
