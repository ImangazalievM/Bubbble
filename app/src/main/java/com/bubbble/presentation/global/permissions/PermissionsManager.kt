package com.bubbble.presentation.global.permissions

import com.afollestad.assent.Permission

interface PermissionsManager {

    suspend fun requestPermission(
        permission: Permission
    ): PermissionResult

    suspend fun requestPermissions(
        vararg permissions: Permission
    ): List<PermissionResult>

    suspend fun checkPermission(
        vararg permissions: Permission
    ): List<PermissionResult>

    suspend fun isGranted(
        vararg permissions: Permission
    ): Boolean

}