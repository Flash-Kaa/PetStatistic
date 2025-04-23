package com.flasska.petstat.presentation.list_of_devices_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.flasska.petstat.R
import com.flasska.petstat.presentation.theme.ui.Black
import com.flasska.petstat.presentation.theme.ui.Platina
import com.flasska.petstat.presentation.theme.ui.White
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.flasska.petstat.presentation.theme.ui.Black2
import com.flasska.petstat.presentation.theme.ui.Gray
import com.flasska.petstat.presentation.theme.ui.GreenForBorder
import com.flasska.petstat.presentation.theme.ui.GreenForTextButton
import com.flasska.petstat.presentation.theme.ui.GreenJungli
import com.flasska.petstat.presentation.theme.ui.LightGray
import com.flasska.petstat.presentation.theme.ui.LightGreen
import com.flasska.petstat.presentation.theme.ui.Mint
import com.flasska.petstat.presentation.theme.ui.Red
import com.flasska.petstat.presentation.theme.ui.Yellow
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import com.flasska.petstat.presentation.navigation.Screen

@Composable
fun ListOfDevicesScreen(
    screenState: ListOfDevicesState,
    screenEvent: (ListOfDevicesEvent) -> Unit,
    navigateTo: (Screen) -> Unit
) {
    // Для цветов используй MaterialTheme.colorScheme.{color} - будет поддержка темной и светлой темы
    // Цвета можно переопределить в theme/ui/Color.kt
    val batteryIcon = painterResource(id = R.drawable.ic_battery)
    val inlineContentId = "batteryIcon"
    val context = LocalContext.current
    var allPermissionsGranted by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // Проверка разрешений на входе
    LaunchedEffect(Unit) {
        val permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.POST_NOTIFICATIONS
        )
        allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Необходимы разрешения")
            },
            text = {
                Text(
                    text = "Для работы приложения необходимо предоставить разрешения. Без них функционал будет недоступен."
                )
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Mint)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Мои устройства",
                    color = Black,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(200.dp)
            ) {
                items(screenState.devices, key = { it.id }) { device ->
                    var isVisible by remember { mutableStateOf(true) }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = { screenEvent(ListOfDevicesEvent.OnDeviceClick(device.id)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = if (device.isActive) GreenForBorder else Gray,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .height(240.dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (device.isActive) White else Gray
                                )
                            )
                            {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Текст с информацией о девайсе
                                    Icon(
                                        painter = painterResource(id = device.avatar), // Иконка аватара
                                        contentDescription = "Аватар",
                                        modifier = if (device.isActive) Modifier.size(64.dp) else Modifier.size(
                                            64.dp
                                        ).alpha(0.45f),
                                        tint = Color.Unspecified
                                    )
                                    if (device.isActive) {
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = Black)) {
                                                    append("${device.name}\n")
                                                }
                                                appendInlineContent(inlineContentId, "[icon]")
                                                append(" ${device.batteryLevel} %")
                                            },
                                            inlineContent = mapOf(
                                                inlineContentId to InlineTextContent(
                                                    placeholder = Placeholder(
                                                        width = 24.sp, // Размер иконки
                                                        height = 24.sp,
                                                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                                                    )
                                                ) {
                                                    Icon(
                                                        painter = batteryIcon,
                                                        contentDescription = "Battery Icon",
                                                        tint = if (device.batteryLevel < 30) Red else if (device.batteryLevel in 30..59) Yellow else GreenForTextButton
                                                    )
                                                }
                                            ),
                                            color = if (device.batteryLevel < 30) Red else if (device.batteryLevel in 30..59) Yellow else GreenForTextButton,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(6.dp)
                                        )
                                    } else {
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = Black2)) {
                                                    append("${device.name}\n")
                                                }
                                                withStyle(style = SpanStyle(color = LightGray)) {
                                                    append("Неактивен")
                                                }
                                            },
                                            color = GreenForTextButton,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(6.dp)
                                        )
                                    }
                                    // Иконка перезагрузки
                                    if (!device.isActive) {
                                        IconButton(
                                            onClick = {
                                                screenEvent(
                                                    ListOfDevicesEvent.OnDeviceReconnect(device.id) // Не реализовано!!
                                                )
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_reload), // Иконка перезагрузки
                                                contentDescription = "Перезагрузка",
                                                tint = if (device.isActive) LightGreen else White
                                            )
                                        }
                                    }

                                    // Иконка удаления
                                    IconButton(
                                        onClick = {
                                            isVisible = false
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete), // Иконка удаления (уже реализовано кое-как)
                                            contentDescription = "Удаление",
                                            tint = if (device.isActive) LightGreen else White
                                        )
                                    }
                                }
                            }
                        }
                    }
                    // Задержка для анимации удаления
                    if (!isVisible) {
                        LaunchedEffect(Unit) {
                            delay(1100)
                            screenEvent(ListOfDevicesEvent.OnDeviceDelete(device.id))
                        }
                    }
                }
            }
            // Элемент "Требуются разрешения"
            if (allPermissionsGranted) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 76.dp)
                        .background(Platina, shape = RoundedCornerShape(16.dp))
                        .border(1.dp, GreenForBorder, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Питомец пока не найден",
                            color = Black,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 76.dp)
                        .background(Platina, shape = RoundedCornerShape(16.dp))
                        .border(1.dp, GreenForBorder, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Требуются разрешения",
                            color = Black,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "Для того, чтобы воспользоваться этой фунцией, необходимо предоставить разрешения. Вы в любое время сможете ограничить разрешения в настройках.",
                            color = Black2,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Left
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Button(
                                onClick = {
                                    showDialog = true
                                    screenEvent(ListOfDevicesEvent.ShowPermissionDialog)
                                }, // Обработчик для кнопки "Отмена"
                                colors = ButtonDefaults.buttonColors(containerColor = White),
                                modifier = Modifier.weight(1f)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(text = "Отмена", color = Black)
                            }
                            Button(
                                onClick = {
                                    navigateTo(Screen.PermissionManagement)
                                }, // Обработчик для кнопки "Перейти"
                                colors = ButtonDefaults.buttonColors(containerColor = GreenJungli),
                                modifier = Modifier.weight(1f)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(text = "Перейти", color = White)
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(vertical = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        if (allPermissionsGranted){
                            screenEvent(ListOfDevicesEvent.AddDevice)
                        }
                    },
                    enabled = allPermissionsGranted,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenJungli),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Добавить устройство",
                        color = White,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}
