package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListOfDevicesScreen(
    screenState: ListOfDevicesState,
    screenEvent: (ListOfDevicesEvent) -> Unit
) {
    // Для цветов используй MaterialTheme.colorScheme.{color} - будет поддержка темной и светлой темы
    // Цвета можно переопределить в theme/ui/Color.kt

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(screenState.devices) {
            Button(
                onClick = { screenEvent(ListOfDevicesEvent.OnDeviceClick(it.id)) }
            ) {
                Text("${it.name} says: \"Remove me pls\"")
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}