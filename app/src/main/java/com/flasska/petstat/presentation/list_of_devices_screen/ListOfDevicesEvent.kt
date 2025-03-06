package com.flasska.petstat.presentation.list_of_devices_screen

sealed class ListOfDevicesEvent {
    data class OnDeviceClick(val uid: String) : ListOfDevicesEvent()
    data object AddDevice : ListOfDevicesEvent()
    data class RemoveDevice(val uid: String) : ListOfDevicesEvent()
}