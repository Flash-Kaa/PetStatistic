package com.flasska.petstat.presentation.list_of_devices_screen

import com.flasska.petstat.domain.entities.Device

data class ListOfDevicesState(
    val devices: List<Device> = emptyList()
)