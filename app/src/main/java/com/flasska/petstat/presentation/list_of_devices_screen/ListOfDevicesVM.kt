package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.petstat.domain.usecases.GetDevicesUseCase
import com.flasska.petstat.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListOfDevicesVM(
    getDevicesUseCase: GetDevicesUseCase,
    private val navigateTo: (Screen) -> Unit
) : ViewModel() {
    private val _state = MutableStateFlow(ListOfDevicesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getDevicesUseCase.invoke().collect { devices ->
                _state.update {
                    ListOfDevicesState(devices)
                }
            }
        }
    }

    fun onEvent(event: ListOfDevicesEvent) {
        when (event) {
            ListOfDevicesEvent.AddDevice -> addDevice()
            is ListOfDevicesEvent.OnDeviceClick -> onDeviceClick(event)
            is ListOfDevicesEvent.RemoveDevice -> TODO()
        }
    }

    private fun addDevice() {
        navigateTo(Screen.AddDevice)
    }

    private fun onDeviceClick(event: ListOfDevicesEvent.OnDeviceClick) {
        navigateTo(Screen.DeviceStatistic(event.uid))
    }

    class FactoryWrapper(
        private val getDevicesUseCase: GetDevicesUseCase
    ) {
        inner class Factory(
            private val navigateTo: (Screen) -> Unit
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListOfDevicesVM(
                    navigateTo = navigateTo,
                    getDevicesUseCase = getDevicesUseCase
                ) as T
            }
        }
    }
}