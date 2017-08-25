package com.imangazalievm.bubbble.presentation.ui.commons;


import android.Manifest;
import android.app.Activity;

import com.imangazalievm.bubbble.presentation.mvp.commons.Permission;
import com.imangazalievm.bubbble.presentation.mvp.commons.PermissionRequestListener;
import com.imangazalievm.bubbble.presentation.mvp.commons.PermissionResult;
import com.imangazalievm.bubbble.presentation.mvp.commons.PermissionsManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

public class AndroidPermissionsManager implements PermissionsManager {

    private Activity activity;
    private RxPermissions rxPermissions;

    public AndroidPermissionsManager(Activity activity) {
        this.activity = activity;
        this.rxPermissions = new RxPermissions(activity);
    }


    @Override
    public void requestPermission(Permission permission, PermissionRequestListener listener) {
        rxPermissions.requestEach(getPermissionAndroidCode(permission))
                .subscribe(result -> listener.onResult(new PermissionResult(permission, result.granted, result.shouldShowRequestPermissionRationale)));
    }

    @Override
    public void requestPermissions(Permission[] permissions, PermissionRequestListener listener) {
        Observable<Permission> requestedPermissions = Observable.fromArray(permissions);
        requestedPermissions
                .map(this::getPermissionAndroidCode)
                .toList()
                .flatMapObservable(permissionCodes -> rxPermissions.requestEach(permissionCodes.toArray(new String[0])))
                .zipWith(requestedPermissions,
                        (result, permission) -> new PermissionResult(permission, result.granted, result.shouldShowRequestPermissionRationale))
                .subscribe(listener::onResult);
    }

    @Override
    public boolean checkPermissionGranted(Permission permission) {
        return rxPermissions.isGranted(getPermissionAndroidCode(permission));
    }

    private String getPermissionAndroidCode(Permission permission) {
        switch (permission) {
            case READ_EXTERNAL_STORAGE:
                return Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }
        return null;
    }

}
