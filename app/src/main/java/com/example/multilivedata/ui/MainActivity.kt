package com.example.multilivedata.ui

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.multilivedata.App
import com.example.multilivedata.R
import com.example.multilivedata.databinding.NewMainContainerBinding
import com.example.multilivedata.network.common.ResponseStatus
import com.example.multilivedata.ui.epoxy.NewsController
import com.example.multilivedata.ui.news.NewsFilter
import com.example.multilivedata.ui.news.NewsFilterDialogFragment

class MainActivity
: AppCompatActivity(), NewsFilterDialogFragment.NewsFilterDialogListener {

    private lateinit var binding: NewMainContainerBinding
    private lateinit var mainViewModel: MainViewModel
    private var mainComponent: MainComponent? = null

    private val onFilterClickListener = View.OnClickListener {
        with (NewsFilterDialogFragment()) {
            arguments = Bundle().apply { putParcelable(NewsFilterDialogFragment.NEWS_FILTER_KEY, mainViewModel.getNewsFilter()) }
            show(supportFragmentManager, "news_filter")
        }
    }

    private val controller = NewsController().also {
        it.filterClickListener = onFilterClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.new_main_container)

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
        binding.recyclerView.setController(controller)
    }

    private fun bindViewModel() {
        mainViewModel.mediatorLiveData.observe(this, Observer {
            if (it == null) return@Observer

            val responseStatus = it.responseStatus
            binding.refreshLayout.isRefreshing = when (responseStatus) {
                ResponseStatus.HAS_CONTENT, ResponseStatus.ERROR, ResponseStatus.NO_CONTENT -> false
                ResponseStatus.LOADING -> true
            }

            val hNews = it.hNews?.data?.let { mainViewModel.mapToNewsItem(it) }
            val vNews = it.vNews?.data?.let { mainViewModel.mapToNewsItem(it) }

            controller.setData(hNews, vNews)
        })
    }

    private fun fetch() = mainViewModel.fetch()

    override fun onDestroy() {
        super.onDestroy()

        mainViewModel.clearComponents()
    }

    override fun onFilterSubmit(filter: NewsFilter) = mainViewModel.fetch(filter)
}
