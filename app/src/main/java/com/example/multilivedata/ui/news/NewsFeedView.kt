package com.example.multilivedata.ui.news

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.multilivedata.databinding.ListViewNewsBinding

class NewsFeedView(
        context: Context,
        attrs : AttributeSet?,
        defStyleAttr: Int
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface NewsFeedListener {
        fun onFilterClick()
    }

    private val binding: ListViewNewsBinding = ListViewNewsBinding.inflate(LayoutInflater.from(context), this, true)
    private val adapter = NewsAdapter()
    private val layoutManager = LinearLayoutManager(context)
    var listener: NewsFeedListener? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context,
                attrs : AttributeSet?) : this(context, attrs, 0)

    init {
        setupRecyclerView()

        binding.newsFilterView.setOnClickListener { listener?.onFilterClick() }
    }

    private fun setupRecyclerView() {
        with (binding.newsRecyclerView) {
            layoutManager = this@NewsFeedView.layoutManager
            adapter = this@NewsFeedView.adapter
        }
    }

    fun setItems(items: List<NewsItem>?) {
        if (items == null) return

        adapter.items = items
        layoutManager.scrollToPositionWithOffset(0, 0)
    }

}