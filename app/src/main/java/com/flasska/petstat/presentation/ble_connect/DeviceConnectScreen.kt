package com.flasska.petstat.presentation.ble_connect

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.flasska.petstat.presentation.theme.ui.GreenForTextButton

@SuppressLint("MissingPermission")
@Composable
fun DeviceConnectScreen(
    screenState: DeviceConnectScreenState,
    screenEvent: (DeviceConnectScreenEvent) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog && screenState.selectedDevice != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        screenEvent(DeviceConnectScreenEvent.OnNavigateNext(screenState.selectedDevice))
                    }
                ) {
                    Text("Подтвердить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Отмена") }
            },
            title = { Text("Введите имя устройства") },
            text = {
                OutlinedTextField(
                    value = screenState.deviceName,
                    onValueChange = { screenEvent(DeviceConnectScreenEvent.OnDeviceNameChanged(it)) },
                    label = { Text("Имя") },
                    singleLine = true
                )
            }
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(screenState.devices) { device ->
            val name = device.device.name ?: device.device.address
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(GreenForTextButton.copy(alpha = 0.5f))
                    .clickable {
                        screenEvent(DeviceConnectScreenEvent.OnDeviceClick(device))
                        showDialog = true
                    }
                    .border(
                        width = 1.dp,
                        color = GreenForTextButton,
                        shape = RoundedCornerShape(8.dp),
                    )
            ) {
                Text(text = name, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
