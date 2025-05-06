package com.flasska.petstat.di

import android.content.Context
import com.flasska.petstat.presentation.ble_connect.BleManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideBleManager(context: Context) : BleManager = BleManager(context)
}