package com.example.multilivedata.network.nytimes.common

import android.support.annotation.StringDef

object TimePeriodDef {
    const val DAY = "1"
    const val WEEK = "7"
    const val MONTH = "30"
}

@StringDef(TimePeriodDef.DAY, TimePeriodDef.WEEK, TimePeriodDef.MONTH)
@Retention(AnnotationRetention.SOURCE)
annotation class TimePeriod

