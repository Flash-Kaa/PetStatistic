package com.flasska.petstat.presentation.ble_connect

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import org.json.JSONObject
import java.util.UUID

@SuppressLint("MissingPermission")
class BleManager(
    private val context: Context,
) {
    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val scanner = bluetoothAdapter.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())
    private var bluetoothGatt: BluetoothGatt? = null


    var onDevicesFound: ((BluetoothDevice) -> Unit)? = null
    var onConnected: (() -> Unit)? = null
    var onDataReceived: ((JSONObject) -> Unit)? = null
    var onDisconnected: (() -> Unit)? = null

    private var bondReceiverDevice: BluetoothDevice? = null

    private val bondReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: android.content.Intent?) {
            val action = intent?.action ?: return
            if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) ?: return
                val bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE)

                if (bondState == BluetoothDevice.BOND_BONDED && device == bondReceiverDevice) {
                    Log.d(TAG, "Bonding finished, now connecting")
                    connectGatt(device)
                } else if (bondState == BluetoothDevice.BOND_NONE) {
                    Log.w(TAG, "Bonding failed or canceled")
                }
            }
        }
    }

    init {
        val filter = android.content.IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        context.registerReceiver(bondReceiver, filter)
    }

    fun startScan() = checkPermissionAndRun {
        if (scanning) return@checkPermissionAndRun

        val filters = listOf(
            ScanFilter.Builder()
                //.setServiceUuid(ParcelUuid(SERVICE_UUID))
                .build()
        )
        val settings = ScanSettings.Builder().build()

        handler.postDelayed(::stopScan, SCAN_PERIOD)

        scanning = true
        scanner.startScan(filters, settings, scanCallback)
        Log.d(TAG, "BLE scan started")
    }

    fun stopScan() = checkPermissionAndRun {
        if (!scanning) return@checkPermissionAndRun
        scanning = false
        scanner.stopScan(scanCallback)
        Log.d(TAG, "BLE scan stopped")
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            result.device?.let {
                onDevicesFound?.invoke(it)
            }
        }
    }

    fun connect(device: BluetoothDevice) = checkPermissionAndRun {
        if (device.bondState == BluetoothDevice.BOND_NONE) {
            Log.d(TAG, "Bonding started")
            device.createBond()
            bondReceiverDevice = device  // сохраним для подключения после bond
        } else if (device.bondState == BluetoothDevice.BOND_BONDED) {
            Log.d(TAG, "Already bonded, connecting")
            connectGatt(device)
        }
    }

    fun disconnect() = checkPermissionAndRun {
        bluetoothGatt?.close()
        bluetoothGatt = null
        onDisconnected?.invoke()
    }

    private inline fun checkPermissionAndRun(action: () -> Unit): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        action()
        return true
    }

    private fun connectGatt(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
        Log.d(TAG, "Connecting to device ${device.address}")
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            checkPermissionAndRun {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d(TAG, "Connected to GATT server.")
                    onConnected?.invoke()
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d(TAG, "Disconnected from GATT server.")
                    onDisconnected?.invoke()
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            checkPermissionAndRun {
                val characteristic = gatt
                    .getService(SERVICE_UUID)
                    ?.getCharacteristic(CHARACTERISTIC_UUID)

                characteristic?.let {
                    gatt.readCharacteristic(it)
                }
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val jsonString = characteristic.value.toString(Charsets.UTF_8)
                try {
                    val jsonObject = JSONObject(jsonString)
                    onDataReceived?.invoke(jsonObject)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to parse JSON", e)
                }
            }
        }
    }

    companion object {
        private const val SCAN_PERIOD: Long = 10000
        private val SERVICE_UUID = UUID.fromString("8a4b4e62-5f1d-4b7d-b16e-68f6e2c7b93d")
        private val CHARACTERISTIC_UUID = UUID.fromString("3a829cdc-fddf-4b93-bf85-083562d5f4ad")
        private const val TAG = "BleManager"
    }
}
