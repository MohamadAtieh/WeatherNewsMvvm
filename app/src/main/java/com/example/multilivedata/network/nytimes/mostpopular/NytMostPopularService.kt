package com.example.multilivedata.network.nytimes.mostpopular

import com.example.multilivedata.network.common.Response
import retrofit2.Call

interface NytMostPopularService {
    fun getMostViewed(section: String, timePeriod: String): Call<Response<MostPopularListing>>
    fun getMostEmailed(section: String, timePeriod: String): Call<Response<MostPopularListing>>
    fun getMostShared(section: String, timePeriod: String): Call<Response<MostPopularListing>>
}