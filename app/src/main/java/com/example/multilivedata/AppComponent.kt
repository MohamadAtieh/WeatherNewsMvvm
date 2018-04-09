package com.example.multilivedata

import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getContext(): Context
}