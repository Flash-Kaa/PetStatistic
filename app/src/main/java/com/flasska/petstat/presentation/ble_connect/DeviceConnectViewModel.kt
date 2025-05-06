package com.flasska.petstat.presentation.ble_connect

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasska.petstat.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceConnectViewModel(
    private val bluetoothManager: BleManager,
    private val navigateTo: (Screen) -> Unit
) : ViewModel() {

    private val _state = MutableStateFlow(DeviceConnectScreenState())
    val state: StateFlow<DeviceConnectScreenState> = _state

    init {
        bluetoothManager.onDevicesFound = { device ->
            viewModelScope.launch(Dispatchers.Default) {
                _state.update {
                    it.copy(
                        devices = (it.devices.toSet() + DeviceConnectScreenState.Device(device)).toList()
                    )
                }
            }
        }

        bluetoothManager.onConnected = {
            viewModelScope.launch(Dispatchers.Default) {
                _state.update { it.copy(isConnected = true) }
            }
        }

        bluetoothManager.onDisconnected = {
            viewModelScope.launch(Dispatchers.Default) {
                _state.update { it.copy(isConnected = false) }
            }
        }

        bluetoothManager.onDataReceived = { data ->
            viewModelScope.launch(Dispatchers.Default) {
                // TODO("add parsing")
                Log.i(TAG, data.toString())
                _state.update { it.copy(receivedData = data.toString()) }
            }
        }

        bluetoothManager.startScan()
    }

    fun onEvent(event: DeviceConnectScreenEvent) {

        viewModelScope.launch(Dispatchers.Default) {
            when (event) {
                is DeviceConnectScreenEvent.OnDeviceClick -> {
                    if (bluetoothManager.connect(event.device.device)) {
                        _state.update { it.copy(selectedDevice = event.device) }
                    }
                    _state.update { it.copy(selectedDevice = null) }
                }

                is DeviceConnectScreenEvent.OnDisconnectClick -> {
                    bluetoothManager.disconnect()
                }

                is DeviceConnectScreenEvent.OnNavigateNext -> {
                    navigateTo(Screen.ListOfDevices)
                }

                is DeviceConnectScreenEvent.OnDeviceNameChanged -> {
                    _state.value = _state.value.copy(deviceName = event.name)
                }
            }
        }
    }

    class FactoryWrapper(
        private val bluetoothManager: BleManager,
    ) {
        inner class Factory(
            private val navigateTo: (Screen) -> Unit
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DeviceConnectViewModel(bluetoothManager, navigateTo) as T
            }
        }
    }

    companion object {
        private const val TAG = "DeviceConnectViewModel"
    }
}