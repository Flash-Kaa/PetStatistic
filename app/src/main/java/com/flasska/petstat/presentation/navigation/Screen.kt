package com.flasska.petstat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object ListOfDevices : Screen

    @Serializable
    data object AddDevice : Screen

    @Serializable
    data class DeviceStatistic(
        val uid: String
    ) : Screen
}