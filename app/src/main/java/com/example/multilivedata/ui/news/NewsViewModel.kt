package com.example.multilivedata.ui.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.multilivedata.network.common.Response
import com.example.multilivedata.network.common.ResponseStatus
import com.example.multilivedata.network.nytimes.common.CategoryDef
import com.example.multilivedata.network.nytimes.mostpopular.MostPopularListing
import com.example.multilivedata.network.nytimes.mostpopular.NytMostPopularService
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

typealias NewsResponse = Response<MostPopularListing>
typealias RetrofitResponse = retrofit2.Response<NewsResponse>

@NewsScope
class NewsViewModel @Inject constructor(private val apiClient: NytMostPopularService) {
    private val news = MutableLiveData<NewsResponse>()

    var newsFilter = NewsFilter() // default filter
    val listing: LiveData<MostPopularListing> = Transformations.map(news, NewsResponse::data)
    val responseStatus: LiveData<ResponseStatus> = Transformations.map(news, NewsResponse::responseStatus)

    fun fetchNews() {
        performCall()
    }

    fun fetchNews(filter: NewsFilter) {
        newsFilter = filter

        performCall()
    }

    private fun performCall() {
        val call = when (newsFilter.category) {
            CategoryDef.MOST_EMAILED -> apiClient.getMostEmailed(newsFilter.section, newsFilter.timePeriod)
            CategoryDef.MOST_SHARED ->  apiClient.getMostShared(newsFilter.section, newsFilter.timePeriod)
            CategoryDef.MOST_VIEWED ->  apiClient.getMostViewed(newsFilter.section, newsFilter.timePeriod)
            else -> null
        } ?: return

        news.value = Response.isLoading()
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>?, response: RetrofitResponse?) {
                val responseBody = response?.body() ?: return

                news.postValue(responseBody)
            }

            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {
                news.postValue(Response.hasFailed(t))
            }
        })
    }

    fun mapToNewsItem(it: MostPopularListing): List<NewsItem>
        = it.results.map {
            val imgUrl = it.media.firstOrNull()?.metadata?.lastOrNull()?.url ?: ""
            NewsItem(it.title, imgUrl, it.url)
        }
}

