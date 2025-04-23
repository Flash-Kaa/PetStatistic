package com.flasska.petstat.presentation.device_statistic_screen

import com.flasska.petstat.domain.entities.Device

data class DeviceStatisticState(
    val device: Device? = null,
    val isLoading: Boolean = true
)