package com.example.multilivedata.network.nytimes.mostpopular

data class MostPopularEntity(
        val id: Long,
        val url: String,
        val title: String,
        val media: List<MediaEntity>
)