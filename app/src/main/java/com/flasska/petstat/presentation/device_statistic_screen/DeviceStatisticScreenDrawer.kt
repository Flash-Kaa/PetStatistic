package com.flasska.petstat.presentation.device_statistic_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.petstat.appComponent
import com.flasska.petstat.presentation.navigation.Screen

@Composable
fun DeviceStatisticScreenDrawer(
    deviceId: String,
    navigateTo: (Screen) -> Unit
) {
    val context = LocalContext.current
    val viewModel = viewModel<DeviceStatisticVM>(
        factory = DeviceStatisticVM.Factory(
            deviceId = deviceId,
            getDevicesUseCase = context.appComponent.provideGetDevicesUseCase(),
            navigateTo = navigateTo
        )
    )
    val state by viewModel.state.collectAsState()

    DeviceStatisticScreen(
        screenState = state,
        screenEvent = viewModel::onEvent
    )
}