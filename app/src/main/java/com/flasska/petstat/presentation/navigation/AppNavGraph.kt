package com.flasska.petstat.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.flasska.petstat.presentation.device_statistic_screen.DeviceStatisticScreenDrawer
import com.flasska.petstat.presentation.list_of_devices_screen.ListOfDevicesScreenDrawer
import com.flasska.petstat.presentation.permissions.PermissionManagementScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ListOfDevices,
        modifier = modifier
    ) {
        composable<Screen.ListOfDevices> {
            ListOfDevicesScreenDrawer(
                navigateTo = navController::navigate
            )
        }

        composable<Screen.AddDevice> {
            Text("Add Device")
        }

        composable<Screen.DeviceStatistic> {
            val args = it.toRoute<Screen.DeviceStatistic>()
            DeviceStatisticScreenDrawer(
                deviceId = args.uid,
                navigateTo = navController::navigate
            )
        }

        composable<Screen.PermissionManagement> {
            PermissionManagementScreen(
                onPermissionsUpdated = {
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}