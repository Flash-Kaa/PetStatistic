package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flasska.petstat.appComponent
import com.flasska.petstat.presentation.navigation.Screen

@Composable
fun ListOfDevicesScreenDrawer(
    navigateTo: (Screen) -> Unit
) {
    val context = LocalContext.current

    val viewModel = viewModel<ListOfDevicesVM>(
        factory = context.appComponent.provideListOfDevicesVMFactoryWrapper().Factory(navigateTo)
    )

    val state by viewModel.state.collectAsState()

    ListOfDevicesScreen(
        screenEvent = viewModel::onEvent,
        screenState = state
    )
}