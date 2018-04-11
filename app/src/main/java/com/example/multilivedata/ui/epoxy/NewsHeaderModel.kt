package com.example.multilivedata.ui.epoxy

import android.databinding.ViewDataBinding
import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.example.multilivedata.R
import com.example.multilivedata.databinding.NewListViewNewsBinding

class NewsHeaderModel(
        var clickListener: View.OnClickListener? = null
) : DataBindingEpoxyModel() {

    override fun getDefaultLayout() = R.layout.new_list_view_news

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? NewListViewNewsBinding)?.newsFilterView?.setOnClickListener(clickListener)
    }

}