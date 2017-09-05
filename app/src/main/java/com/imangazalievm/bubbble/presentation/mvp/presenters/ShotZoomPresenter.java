package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.interactors.ShotZoomInteractor;
import com.imangazalievm.bubbble.presentation.commons.permissions.Permission;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotZoomView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;
import javax.inject.Named;

@InjectViewState
public class ShotZoomPresenter extends MvpPresenter<ShotZoomView> {

    private ShotZoomInteractor shotZoomInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private PermissionsManager permissionsManager;
    private String shotTitle;
    private String shotUrl;
    private String imageUrl;

    @Inject
    public ShotZoomPresenter(ShotZoomInteractor shotZoomInteractor,
                             RxSchedulersProvider rxSchedulersProvider,
                             @Named("shot_title") String shotTitle,
                             @Named("shot_url") String shotUrl,
                             @Named("image_url") String imageUrl) {
        this.shotZoomInteractor = shotZoomInteractor;
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.shotTitle = shotTitle;
        this.shotUrl = shotUrl;
        this.imageUrl = imageUrl;
    }

    public void setPermissionsManager(PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

    public void removePermissionsManager() {
        permissionsManager = null;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

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
                .compose(rxSchedulersProvider.getIoToMainTransformerCompletableCompletable())
                .subscribe(() -> getViewState().showImageSavedMessage(), DebugUtils::showDebugErrorMessage);
    }

    public void onOpenInBrowserClicked() {
        getViewState().openInBrowser(shotUrl);
    }

    public void onShareClicked() {
        getViewState().showShotSharing(shotTitle, shotUrl);
    }

}
