package com.flasska.petstat.presentation.ble_connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.petstat.appComponent
import com.flasska.petstat.presentation.navigation.Screen

@Composable
fun DeviceConnectScreenDrawer(
    navigateTo: (Screen) -> Unit
) {
    val context = LocalContext.current
    val viewModel = viewModel<DeviceConnectViewModel>(
        factory = context.appComponent
            .provideDeviceConnectViewModel()
            .Factory(navigateTo)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    DeviceConnectScreen(
        screenState = state,
        screenEvent = viewModel::onEvent
    )
}