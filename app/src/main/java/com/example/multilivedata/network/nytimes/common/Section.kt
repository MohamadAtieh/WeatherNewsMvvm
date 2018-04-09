package com.example.multilivedata.network.nytimes.common

import android.support.annotation.StringDef

object SectionDef {
    const val ALL = "all-sections"
    const val BOOKS = "Books"
    const val FOOD = "Food"
    const val MOVIES = "Movies"
    const val SPORTS = "Sports"
}

@StringDef(SectionDef.ALL, SectionDef.BOOKS, SectionDef.FOOD, SectionDef.MOVIES, SectionDef.SPORTS)
@Retention(AnnotationRetention.SOURCE)
annotation class Section

