package com.example.multilivedata.network.nytimes.common

import android.support.annotation.StringDef

object CategoryDef {
    const val MOST_VIEWED = "mostviewed"
    const val MOST_EMAILED = "mostemailed"
    const val MOST_SHARED = "mostshared"
}

@StringDef(CategoryDef.MOST_VIEWED, CategoryDef.MOST_EMAILED, CategoryDef.MOST_SHARED)
@Retention(AnnotationRetention.SOURCE)
annotation class Category

