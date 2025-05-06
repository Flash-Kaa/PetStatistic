package com.flasska.petstat.presentation.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.flasska.petstat.presentation.theme.ui.Mint

@Composable
fun PermissionManagementScreen(
    onPermissionsUpdated: () -> Unit, onBack: () -> Unit
) {
    val permissions = listOf(
        Triple(
            Manifest.permission.ACCESS_FINE_LOCATION,
            "Доступ к геолокации",
            "Он позволит приложению определять ваше местоположение для предоставления персонализированных данных."
        ), Triple(
            Manifest.permission.BLUETOOTH_CONNECT,
            "Доступ к Bluetooth",
            "Необходим для передачи данных в реальном времени"
        ), Triple(
            Manifest.permission.POST_NOTIFICATIONS,
            "Доступ к уведомлениям",
            "Позволяет отправлять уведомления о важных событиях и обновлениях."
        ), Triple(
            Manifest.permission.BLUETOOTH_SCAN,
            "Доступ к поиску устройств",
            "Позволяет найти устройство по близости."
        )
    )
    val context = LocalContext.current
    val permissionsState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            permissions.forEach { (permission, _) ->
                this[permission] = ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Mint)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Назад"
                )
            }

            Text(
                text = "Управление разрешениями",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 54.dp, horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        permissions.forEachIndexed { index, (permission, description, detailedDescription) ->
            PermissionItem(permission = permission,
                description = description,
                detailedDescription = detailedDescription,
                isGranted = permissionsState[permission] == true,
                onPermissionResult = { granted ->
                    permissionsState[permission] = granted
                    onPermissionsUpdated()
                })
            if (index < permissions.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PermissionItem(
    permission: String,
    description: String,
    detailedDescription: String,
    isGranted: Boolean,
    onPermissionResult: (Boolean) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                onPermissionResult(granted)
            })

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = description, style = MaterialTheme.typography.bodyLarge, fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detailedDescription,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Button(
            onClick = {
                launcher.launch(permission)
            }, enabled = !isGranted
        ) {
            Text(text = if (isGranted) "Разрешено" else "Разрешить")
        }
    }
}
