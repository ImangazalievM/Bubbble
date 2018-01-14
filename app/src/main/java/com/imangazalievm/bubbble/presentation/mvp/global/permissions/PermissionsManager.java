package com.imangazalievm.bubbble.presentation.mvp.global.permissions;

public interface PermissionsManager {

    void requestPermission(Permission permission, PermissionRequestListener listener);

    void requestPermissions(Permission[] permissions, PermissionRequestListener listener);

    boolean checkPermissionGranted(Permission permission);

}
