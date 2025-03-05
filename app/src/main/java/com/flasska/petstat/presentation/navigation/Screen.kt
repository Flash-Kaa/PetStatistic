package com.flasska.petstat.presentation.navigation

sealed class Screen(val route: String) {
    data object ListOfDevices : Screen("list_of_devices_screen")
    data object AddDevice : Screen("add_device_screen")
    data object DeviceStatistic : Screen("device_stats_screen")
}