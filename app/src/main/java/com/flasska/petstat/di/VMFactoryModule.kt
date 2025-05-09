package com.flasska.petstat.di

import com.flasska.petstat.domain.usecases.GetDevicesUseCase
import com.flasska.petstat.presentation.ble_connect.BleManager
import com.flasska.petstat.presentation.ble_connect.DeviceConnectViewModel
import com.flasska.petstat.presentation.list_of_devices_screen.ListOfDevicesVM
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class VMFactoryModule {
    @Provides
    @Singleton
    fun provideListOfDevicesViewModelFactoryWrapper(
        getDevicesUseCase: GetDevicesUseCase
    ) = ListOfDevicesVM.FactoryWrapper(
        getDevicesUseCase = getDevicesUseCase,
    )

    @Provides
    @Singleton
    fun provideDeviceConnectViewModel(
        bluetoothManager: BleManager,
    ) : DeviceConnectViewModel.FactoryWrapper = DeviceConnectViewModel.FactoryWrapper(
        bluetoothManager = bluetoothManager,
    )
}