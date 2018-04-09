package com.example.multilivedata

import android.app.Application

class App : Application() {

    val appComponent: AppComponent
        = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

}
