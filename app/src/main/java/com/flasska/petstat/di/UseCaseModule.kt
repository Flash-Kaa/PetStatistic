package com.flasska.petstat.di

import com.flasska.petstat.domain.usecases.GetDevicesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetDevicesUseCase() = GetDevicesUseCase()
}