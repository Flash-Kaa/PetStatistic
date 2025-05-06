package com.flasska.petstat.presentation.ble_connect

sealed class DeviceConnectScreenEvent {
    data class OnDeviceClick(val device: DeviceConnectScreenState.Device) :
        DeviceConnectScreenEvent()

    data class OnNavigateNext(val device: DeviceConnectScreenState.Device) :
        DeviceConnectScreenEvent()

    data object OnDisconnectClick : DeviceConnectScreenEvent()
    data class OnDeviceNameChanged(val name: String) : DeviceConnectScreenEvent()
}
