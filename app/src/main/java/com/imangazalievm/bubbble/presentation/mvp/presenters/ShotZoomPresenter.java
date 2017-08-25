package com.imangazalievm.bubbble.presentation.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.di.ShotZoomPresenterComponent;
import com.imangazalievm.bubbble.domain.interactors.ShotZoomInteractor;
import com.imangazalievm.bubbble.presentation.mvp.commons.Permission;
import com.imangazalievm.bubbble.presentation.mvp.commons.PermissionsManager;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotZoomView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class ShotZoomPresenter extends MvpPresenter<ShotZoomView> {

    @Inject
    ShotZoomInteractor shotZoomInteractor;

    private PermissionsManager permissionsManager;
    private String shotTitle;
    private String shotUrl;
    private String imageUrl;

    public ShotZoomPresenter(ShotZoomPresenterComponent presenterComponent,
                             PermissionsManager permissionsManager,
                             String shotTitle, String imageUrl, String shotUrl) {
        this.permissionsManager = permissionsManager;
        this.shotTitle = shotTitle;
        this.imageUrl = imageUrl;
        this.shotUrl = shotUrl;
        presenterComponent.inject(this);

        showShot();
    }

    private void showShot() {
        getViewState().showShot(imageUrl);
    }

    public void onImageLoadSuccess() {
        getViewState().hideLoadingProgress();
    }

    public void onImageLoadError() {
        getViewState().hideLoadingProgress();
        getViewState().showErrorLayout();
    }

    public void onDownloadImageClicked() {
        if (permissionsManager.checkPermissionGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage();
        } else {
            permissionsManager.requestPermission(Permission.READ_EXTERNAL_STORAGE, permissionResult -> {
                if (permissionResult.granted) {
                    saveShotImage();
                } else if (permissionResult.shouldShowRequestPermissionRationale) {
                    getViewState().showStorageAccessRationaleMessage();
                } else {
                    getViewState().showAllowStorageAccessMessage();
                }
            });
        }
    }

    public void onAppSettingsButtonClicked() {
        getViewState().openAppSettingsScreen();
    }

    private void saveShotImage() {
        shotZoomInteractor.saveImage(imageUrl)
                .subscribe(() -> getViewState().showImageSavedMessage(), throwable -> DebugUtils.showDebugErrorMessage(throwable));
    }

    public void onOpenInBrowserClicked() {
        getViewState().openInBrowser(shotUrl);
    }

    public void onShareClicked() {
        getViewState().showShotSharing(shotTitle, shotUrl);
    }
    
}
