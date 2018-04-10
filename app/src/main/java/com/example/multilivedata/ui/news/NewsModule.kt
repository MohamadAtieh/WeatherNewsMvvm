package com.example.multilivedata.ui.news

import com.example.multilivedata.network.nytimes.NytMostPopularApiClient
import com.example.multilivedata.network.nytimes.mostpopular.NytMostPopularService
import dagger.Module
import dagger.Provides

@Module
class NewsModule {
    @Provides @NewsScope
    fun provideNytApiClient(): NytMostPopularService = NytMostPopularApiClient()

    @Provides
    fun provideNewsViewModel(apiClient: NytMostPopularService) = NewsViewModel(apiClient)
}