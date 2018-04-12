package com.example.multilivedata.ui

import android.arch.lifecycle.MediatorLiveData
import android.os.Parcelable
import com.example.multilivedata.network.common.ResponseStatus
import com.example.multilivedata.network.common.ResponseStatusInterface
import com.example.multilivedata.network.nytimes.mostpopular.MostPopularListing
import com.example.multilivedata.ui.news.NewsFilter
import com.example.multilivedata.ui.news.NewsItem
import com.example.multilivedata.ui.news.NewsResponse
import com.example.multilivedata.ui.news.NewsViewModel
import javax.inject.Inject

data class MainPageModel(
        override var responseStatus: ResponseStatus,
        var hNews: NewsResponse? = null,
        var vNews: NewsResponse? = null
) : ResponseStatusInterface

@ActivityScope
class MainViewModel @Inject constructor(private val mainInjector: MainInjector) {

    val mediatorLiveData = MediatorLiveData<MainPageModel>()

    private val newsComponent = mainInjector.plusNewsComponent()
    private var hNewsViewModel: NewsViewModel = newsComponent.getNewsViewModel()
    private var vNewsViewModel: NewsViewModel = newsComponent.getNewsViewModel()

    init {
        mediatorLiveData.addSource(hNewsViewModel.news, {
            val pageModel = mediatorLiveData.value ?: return@addSource
            pageModel.hNews = it
            evaluateResponseStatus(pageModel)

            mediatorLiveData.value = pageModel
        })

        mediatorLiveData.addSource(vNewsViewModel.news, {
            val pageModel = mediatorLiveData.value ?: return@addSource
            pageModel.vNews = it
            evaluateResponseStatus(pageModel)

            mediatorLiveData.value = pageModel
        })
    }

    private fun evaluateResponseStatus(pageModel: MainPageModel) {
        val hRS = pageModel.hNews?.responseStatus ?: return
        val vRS = pageModel.vNews?.responseStatus ?: return

        if (hRS == ResponseStatus.LOADING || vRS == ResponseStatus.LOADING) {
            pageModel.responseStatus = ResponseStatus.LOADING
        } else if (hRS == ResponseStatus.ERROR || vRS == ResponseStatus.ERROR) {
            pageModel.responseStatus = ResponseStatus.ERROR
        } else if (hRS == ResponseStatus.NO_CONTENT && vRS == ResponseStatus.NO_CONTENT) {
            pageModel.responseStatus = ResponseStatus.NO_CONTENT
        } else if (hRS == ResponseStatus.HAS_CONTENT && vRS == ResponseStatus.HAS_CONTENT) {
            pageModel.responseStatus = ResponseStatus.HAS_CONTENT
        }
    }

    fun fetch() {
        mediatorLiveData.value = MainPageModel(ResponseStatus.LOADING) //initial

        hNewsViewModel.fetchNews()
        vNewsViewModel.fetchNews()
    }

    fun fetch(filter: NewsFilter) {
        mediatorLiveData.value = MainPageModel(ResponseStatus.LOADING) //initial

        hNewsViewModel.fetchNews(filter)
        vNewsViewModel.fetchNews(filter)
    }

    fun clearComponents() {
        mainInjector.clearNewsComponent()
    }

    fun getNewsFilter(): Parcelable? = hNewsViewModel.newsFilter // any

    fun mapToNewsItem(it: MostPopularListing): List<NewsItem>
            = it.results.map {
        val imgUrl = it.media.firstOrNull()?.metadata?.lastOrNull()?.url ?: ""
        NewsItem(it.id, it.title, imgUrl, it.url)
    }
}