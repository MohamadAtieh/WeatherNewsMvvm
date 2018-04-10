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
    private lateinit var mainViewModel: MainViewModel
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

        mainViewModel = mainComponent?.getMainViewModel() ?: return
    }

    private fun setupViews() {
        binding.refreshLayout.setOnRefreshListener { fetch() }

        binding.upperNewsListView.listener = this

        binding.newsListView.news_header_view.visibility = GONE
        binding.newsListView.news_filter_view.visibility = GONE
    }

    private fun bindViewModel() {
        mainViewModel.mediatorLiveData.observe(this, Observer {
            if (it == null) return@Observer

            val responseStatus = it.responseStatus
            binding.refreshLayout.isRefreshing = when (responseStatus) {
                ResponseStatus.HAS_CONTENT, ResponseStatus.ERROR, ResponseStatus.NO_CONTENT -> false
                ResponseStatus.LOADING -> true
            }

            it.hNews?.data?.let { data ->
                binding.upperNewsListView.setItems(mainViewModel.mapToNewsItem(data))
            }

            it.vNews?.data?.let { data ->
                binding.newsListView.setItems(mainViewModel.mapToNewsItem(data))
            }
        })
    }

    private fun fetch() = mainViewModel.fetch()

    override fun onDestroy() {
        super.onDestroy()

        mainViewModel.clearComponents()
    }

    override fun onFilterClick() {
        with (NewsFilterDialogFragment()) {
            arguments = Bundle().apply { putParcelable(NewsFilterDialogFragment.NEWS_FILTER_KEY, mainViewModel.getNewsFilter()) }
            show(supportFragmentManager, "news_filter")
        }
    }

    override fun onFilterSubmit(filter: NewsFilter) = mainViewModel.fetch(filter)
}
