package com.flasska.petstat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flasska.petstat.presentation.list_of_devices_screen.ListOfDevicesScreenDrawer

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ListOfDevices.route,
        modifier = modifier
    ) {
        composable(Screen.ListOfDevices.route) {
            ListOfDevicesScreenDrawer(
                navigateTo = navController::navigate
            )
        }

        composable(Screen.AddDevice.route) {

        }

        composable(Screen.DeviceStatistic.route) {

        }
    }
}