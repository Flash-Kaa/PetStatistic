package com.flasska.petstat.presentation.ble_connect

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

data class DeviceConnectScreenState(
    val devices: List<Device> = emptyList(),
    val isConnected: Boolean = false,
    val receivedData: String = "",
    val selectedDevice: Device? = null,
    val deviceName: String = "",
) {
    @SuppressLint("MissingPermission")
    class Device(val device: BluetoothDevice) {
        override fun hashCode(): Int {
            return (device.name ?: device.address ?: device.type).hashCode()
        }

        override fun equals(other: Any?): Boolean {
            return (other as? Device)?.let {
                with(it.device) {
                    name == device.name && address == device.address
                }
            } == true
        }
    }
}
