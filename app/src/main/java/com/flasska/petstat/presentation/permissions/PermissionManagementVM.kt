package com.flasska.petstat.presentation.permissions

import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel

class PermissionManagementVM : ViewModel() {
    fun requestPermission(context: Context, permission: String, onPermissionResult: () -> Unit) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context as android.app.Activity, permission)) {
        } else {
            ActivityCompat.requestPermissions(context, arrayOf(permission), 0)
            onPermissionResult()
        }
    }
}