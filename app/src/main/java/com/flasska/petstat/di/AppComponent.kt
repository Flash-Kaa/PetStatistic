package com.flasska.petstat.di

import android.content.Context
import com.flasska.petstat.domain.usecases.GetDevicesUseCase
import com.flasska.petstat.presentation.list_of_devices_screen.ListOfDevicesVM
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [VMFactoryModule::class, UseCaseModule::class])
interface AppComponent {
    fun provideListOfDevicesVMFactoryWrapper(): ListOfDevicesVM.FactoryWrapper

    fun provideGetDevicesUseCase() : GetDevicesUseCase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}