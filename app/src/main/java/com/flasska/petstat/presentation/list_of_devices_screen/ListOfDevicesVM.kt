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
            is ListOfDevicesEvent.OnDeviceReconnect -> onDeviceReconnect(event)
            is ListOfDevicesEvent.OnDeviceDelete -> onDeviceDelete(event)
            ListOfDevicesEvent.ShowPermissionDialog -> showPermissionDialog()
        }
    }

    private fun showPermissionDialog() {
        // Логика для отображения диалога
    }

    private fun addDevice() {
        if (state.value.devices.isNotEmpty()) {
            navigateTo(Screen.AddDevice)
        }
    }

    private fun onDeviceClick(event: ListOfDevicesEvent.OnDeviceClick) {
        val device = state.value.devices.find { it.id == event.uid }
        if (device != null) {
            navigateTo(Screen.DeviceStatistic(uid = device.id))
        }
    }

    private fun onDeviceDelete(event: ListOfDevicesEvent.OnDeviceDelete) {
        _state.update { currentState ->
            currentState.copy(
                devices = currentState.devices.filter { it.id != event.uid }
            )
        }
    }

    private fun onDeviceReconnect(event: ListOfDevicesEvent.OnDeviceReconnect) {
        // Заглушка для перезагрузки устройства
        println("Перезагрузка устройства с ID: ${event.uid}")
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