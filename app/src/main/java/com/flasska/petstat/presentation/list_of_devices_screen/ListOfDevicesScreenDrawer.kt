package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.petstat.presentation.navigation.Screen

@Composable
fun ListOfDevicesScreenDrawer(
    navigateTo: (Screen) -> Unit
) {

    val viewModel = viewModel<ListOfDevicesVM>(
        factory = ListOfDevicesVM.ListOfDevicesFactory().Factory(navigateTo)
    )

    val state by viewModel.state.collectAsState()

    ListOfDevicesScreen(
        screenEvent = viewModel::onEvent,
        screenState = state
    )
}