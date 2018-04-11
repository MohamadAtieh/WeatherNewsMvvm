package com.example.multilivedata.ui.epoxy

import android.view.View
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.multilivedata.ui.news.NewsItem

class NewsController: EpoxyController() {

    @AutoModel lateinit var newsHeader: NewsHeaderModel
    @AutoModel lateinit var carouselModel: CarouselModel_

    var filterClickListener: View.OnClickListener? = null
    var hNews = emptyList<NewsItem>()
    var vNews = emptyList<NewsItem>()

    override fun buildModels() {
        with (newsHeader) {
            filterClickListener?.let { this.clickListener = it }
            addTo(this@NewsController)
        }

        with (carouselModel) {
            val carouselModels = mutableListOf<NewsItemModel>()
            hNews.forEach {
                carouselModels.add(NewsItemModel(it.title, it.url, it.imageUrl))
            }
            models(carouselModels)
            addTo(this@NewsController)
        }

        vNews.forEachIndexed { index, item ->
            NewsItemModel(item.title, item.url, item.imageUrl).id(index).addTo(this)
        }
    }
}