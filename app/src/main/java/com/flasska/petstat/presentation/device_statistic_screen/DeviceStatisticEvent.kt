package com.flasska.petstat.presentation.device_statistic_screen

sealed class DeviceStatisticEvent {
    data object Refresh : DeviceStatisticEvent()
    data object NavigateBack : DeviceStatisticEvent()
}