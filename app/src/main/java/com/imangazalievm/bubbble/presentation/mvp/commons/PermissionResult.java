package com.imangazalievm.bubbble.presentation.mvp.commons;

public class PermissionResult {

    public final Permission permission;
    public final boolean granted;
    public final boolean shouldShowRequestPermissionRationale;

    public PermissionResult(Permission permission, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.permission = permission;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }
}
