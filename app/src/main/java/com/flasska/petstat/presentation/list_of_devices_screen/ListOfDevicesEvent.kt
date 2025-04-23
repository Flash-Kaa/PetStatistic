package com.flasska.petstat.presentation.list_of_devices_screen

sealed class ListOfDevicesEvent {
    data class OnDeviceClick(val uid: String) : ListOfDevicesEvent()
    data object AddDevice : ListOfDevicesEvent()
    data class OnDeviceReconnect(val uid: String) : ListOfDevicesEvent()
    data class OnDeviceDelete(val uid: String) : ListOfDevicesEvent()
    data object ShowPermissionDialog : ListOfDevicesEvent()
}