package com.example.multilivedata.ui.epoxy

import android.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.example.multilivedata.R
import com.example.multilivedata.databinding.ItemViewNewsBinding
import com.example.multilivedata.network.common.GlideApp

data class NewsItemModel(
        private var newsTitle: String? = null,
        private var newsUrl: String? = null,
        private var newsImgUrl: String? = null
) : DataBindingEpoxyModel() {

    override fun getDefaultLayout() = R.layout.item_view_news

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        if (binding == null) return

        val context = binding.root.context

        (binding as? ItemViewNewsBinding)?.let {
            it.newsTitle.text = newsTitle
            GlideApp.with(context)
                    .load(newsImgUrl)
                    .fitCenter()
                    .into(it.newsImage)
        }
    }
}