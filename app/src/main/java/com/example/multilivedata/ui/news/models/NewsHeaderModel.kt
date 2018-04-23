package com.example.multilivedata.ui.news.models

import android.databinding.ViewDataBinding
import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.example.multilivedata.R
import com.example.multilivedata.databinding.ItemViewNewsListHeaderBinding

class NewsHeaderModel(
        var clickListener: View.OnClickListener? = null
) : DataBindingEpoxyModel() {

    override fun getDefaultLayout() = R.layout.item_view_news_list_header

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? ItemViewNewsListHeaderBinding)?.newsFilterView?.setOnClickListener(clickListener)
    }
}