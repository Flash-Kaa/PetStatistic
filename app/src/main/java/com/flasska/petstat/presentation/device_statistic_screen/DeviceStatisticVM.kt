package com.flasska.petstat.presentation.device_statistic_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.petstat.domain.usecases.GetDevicesUseCase
import com.flasska.petstat.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceStatisticVM(
    private val deviceId: String,
    private val getDevicesUseCase: GetDevicesUseCase,
    private val navigateTo: (Screen) -> Unit
) : ViewModel() {
    private val _state = MutableStateFlow(DeviceStatisticState())
    val state = _state.asStateFlow()

    init {
        loadDevice()
    }

    private fun loadDevice() {
        viewModelScope.launch {
            getDevicesUseCase.invoke().collect { devices ->
                val device = devices.find { it.id == deviceId }
                _state.update {
                    it.copy(device = device, isLoading = false)
                }
            }
        }
    }

    fun onEvent(event: DeviceStatisticEvent) {
        when (event) {
            DeviceStatisticEvent.Refresh -> loadDevice()
            DeviceStatisticEvent.NavigateBack -> navigateTo(Screen.ListOfDevices)
        }
    }

    class Factory(
        private val deviceId: String,
        private val getDevicesUseCase: GetDevicesUseCase,
        private val navigateTo: (Screen) -> Unit
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DeviceStatisticVM(deviceId, getDevicesUseCase, navigateTo) as T
        }
    }
}