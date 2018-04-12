package com.example.multilivedata.ui.epoxy

import android.view.View
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.example.multilivedata.ui.news.NewsItem

class NewsController: Typed2EpoxyController<List<NewsItem>, List<NewsItem>>() {

    private val newsHeader = NewsHeaderModel()

    var filterClickListener: View.OnClickListener? = null

    override fun buildModels(hNews: List<NewsItem>?, vNews: List<NewsItem>?) {
        with (newsHeader) {
            id("header")
            filterClickListener?.let { this.clickListener = it }
            addTo(this@NewsController)
        }

        val carouselModel = CarouselModel_()
        with (carouselModel) {
            val models = mutableListOf<NewsItemModel>()
            hNews?.forEach {
                val model = NewsItemModel(it.title, it.url, it.imageUrl).id(it.id) as NewsItemModel
                models.add(model)
            }

            id("carousel")
            models(models)
            addTo(this@NewsController)
        }

        vNews?.forEach { item ->
            NewsItemModel(item.title, item.url, item.imageUrl).apply {
                id(item.id)
                addTo(this@NewsController)
            }
        }
    }
}