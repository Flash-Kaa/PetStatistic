package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.petstat.domain.entities.Device
import com.flasska.petstat.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ListOfDevicesVM(
    private val navigateTo: (Screen) -> Unit
) : ViewModel() {
    private val _state = MutableStateFlow(ListOfDevicesState())
    val state = _state.asStateFlow()

    // TODO: remove
    init {
        viewModelScope.launch {
            val devices = (0..6).map {
                val uuid = UUID.randomUUID().toString()
                Device(uuid, uuid)
            }

            _state.update {
                ListOfDevicesState(devices)
            }
        }
    }

    fun onEvent(event: ListOfDevicesEvent) {
        when (event) {
            ListOfDevicesEvent.AddDevice -> addDevice()
            is ListOfDevicesEvent.OnDeviceClick -> TODO()
            is ListOfDevicesEvent.RemoveDevice -> TODO()
        }
    }

    private fun addDevice() {
        navigateTo(Screen.AddDevice)
    }

    class ListOfDevicesFactory {
        inner class Factory(
            private val navigateTo: (Screen) -> Unit
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListOfDevicesVM(
                    navigateTo = navigateTo
                ) as T
            }
        }
    }
}