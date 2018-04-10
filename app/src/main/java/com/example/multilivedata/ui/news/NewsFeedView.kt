package com.example.multilivedata.ui.news

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.multilivedata.R
import com.example.multilivedata.databinding.ListViewNewsBinding

class NewsFeedView : ConstraintLayout {

    enum class ListingOrientation {
        VERTICAL,
        HORIZONTAL
    }

    interface NewsFeedListener {
        fun onFilterClick()
    }

    companion object {
        private const val VERTICAL = 0
        private const val HORIZONTAL = 1
    }

    private val binding: ListViewNewsBinding = ListViewNewsBinding.inflate(LayoutInflater.from(context), this, true)

    private var listOrientation: ListingOrientation = ListingOrientation.VERTICAL
        set(value) {
            field = value

            val orientation = when (value) {
                ListingOrientation.VERTICAL -> LinearLayoutManager.VERTICAL
                ListingOrientation.HORIZONTAL -> LinearLayoutManager.HORIZONTAL
            }

            layoutManager = LinearLayoutManager(context, orientation, false)
            setupRecyclerView()
        }

    var listener: NewsFeedListener? = null
    private val adapter = NewsAdapter()
    private lateinit var layoutManager: LinearLayoutManager

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs : AttributeSet?) : super(context, attrs) {
        val attributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NewsFeedView,
                0,
                0
        )

        try {
            val value = attributes.getInteger(R.styleable.NewsFeedView_orientation, VERTICAL)
            listOrientation = when (value) {
                VERTICAL -> ListingOrientation.VERTICAL
                HORIZONTAL -> ListingOrientation.HORIZONTAL
                else -> ListingOrientation.VERTICAL
            }
        } finally {
            attributes.recycle()
        }
    }

    init {
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