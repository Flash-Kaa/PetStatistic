package com.flasska.petstat

import android.app.Application
import com.flasska.petstat.di.AppComponent
import com.flasska.petstat.di.DaggerAppComponent

class PetStatApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}