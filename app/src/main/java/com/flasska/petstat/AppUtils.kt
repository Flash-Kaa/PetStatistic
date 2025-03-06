package com.flasska.petstat

import android.content.Context
import com.flasska.petstat.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is PetStatApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }