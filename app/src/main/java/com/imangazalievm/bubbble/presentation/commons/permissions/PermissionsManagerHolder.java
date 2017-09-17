package com.imangazalievm.bubbble.presentation.commons.permissions;

import javax.inject.Inject;

public class PermissionsManagerHolder {

    private PermissionsManager permissionsManager;

    @Inject
    public PermissionsManagerHolder() {

    }

    public void setPermissionsManager(PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

    public void removePermissionsManager() {
        this.permissionsManager = null;
    }

    public void requestPermission(Permission permission, PermissionRequestListener listener) {
        if (permissionsManager != null) {
            permissionsManager.requestPermission(permission, listener);
        }
    }

    public void requestPermissions(Permission[] permissions, PermissionRequestListener listener) {
        if (permissionsManager != null) {
            permissionsManager.requestPermissions(permissions, listener);
        }
    }

    public boolean checkPermissionGranted(Permission permission) {
        return permissionsManager != null && permissionsManager.checkPermissionGranted(permission);
    }

}
