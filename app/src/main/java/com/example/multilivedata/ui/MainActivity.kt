package com.example.multilivedata.ui

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import com.example.multilivedata.App
import com.example.multilivedata.R
import com.example.multilivedata.databinding.MainContainerBinding
import com.example.multilivedata.network.common.ResponseStatus
import com.example.multilivedata.ui.news.NewsFeedView
import com.example.multilivedata.ui.news.NewsFilter
import com.example.multilivedata.ui.news.NewsFilterDialogFragment
import com.example.multilivedata.ui.news.NewsViewModel
import kotlinx.android.synthetic.main.list_view_news.view.*

class MainActivity
: AppCompatActivity(), NewsFeedView.NewsFeedListener, NewsFilterDialogFragment.NewsFilterDialogListener {

    private lateinit var binding: MainContainerBinding
    private lateinit var newsViewModel: NewsViewModel
    private var mainComponent: MainComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_container)

        setupComponent()
        setupViews()
        bindViewModel()
        fetch()
    }

    private fun setupComponent() {
        val appComponent = (application as? App)?.appComponent ?: return

        mainComponent = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(MainModule())
                .build()

        newsViewModel = mainComponent?.getInjector()?.plusNewsComponent()?.getNewsViewModel() ?: return
    }

    private fun setupViews() {
        binding.refreshLayout.setOnRefreshListener { fetch() }
        binding.newsListView.listener = this
    }

    private fun bindViewModel() {
        newsViewModel.listing.observe(this, Observer {
            if (it == null) return@Observer

            // todo separate view model
            binding.upperNewsListView.setItems(newsViewModel.mapToNewsItem(it))
            binding.upperNewsListView.news_header_view.visibility = GONE
            binding.upperNewsListView.news_filter_view.visibility = GONE

            binding.newsListView.setItems(newsViewModel.mapToNewsItem(it))
        })

        newsViewModel.responseStatus.observe(this, Observer {
            binding.refreshLayout.isRefreshing = when (it) {
                ResponseStatus.HAS_CONTENT, ResponseStatus.ERROR, ResponseStatus.NO_CONTENT -> false
                ResponseStatus.LOADING -> true
                else -> false
            }
        })
    }

    private fun fetch() = newsViewModel.fetchNews() // default call

    override fun onDestroy() {
        super.onDestroy()

        mainComponent?.getInjector()?.clearNewsComponent()
    }

    override fun onFilterClick() {
        with (NewsFilterDialogFragment()) {
            arguments = Bundle().apply { putParcelable(NewsFilterDialogFragment.NEWS_FILTER_KEY, newsViewModel.newsFilter) }
            show(supportFragmentManager, "news_filter")
        }
    }

    override fun onFilterSubmit(filter: NewsFilter) = newsViewModel.fetchNews(filter)
}
