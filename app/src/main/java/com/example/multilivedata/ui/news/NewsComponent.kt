package com.example.multilivedata.ui.news

import com.example.multilivedata.network.nytimes.mostpopular.NytMostPopularService
import dagger.Subcomponent

@NewsScope
@Subcomponent(modules = [NewsModule::class])
interface NewsComponent {
    fun getMostPopularApiClient() : NytMostPopularService
    fun getNewsViewModel() : NewsViewModel
}