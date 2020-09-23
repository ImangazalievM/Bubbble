package com.imangazalievm.bubbble.presentation.mvp.shotzoom;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.shotzoom.ShotZoomInteractor;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.global.permissions.Permission;
import com.imangazalievm.bubbble.presentation.mvp.global.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.mvp.global.permissions.PermissionsManagerHolder;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;
import javax.inject.Named;

@InjectViewState
public class ShotZoomPresenter extends MvpPresenter<ShotZoomView> {

    private ShotZoomInteractor shotZoomInteractor;
    private SchedulersProvider schedulersProvider;
    private PermissionsManagerHolder permissionsManagerHolder;
    private String shotTitle;
    private String shotUrl;
    private String imageUrl;

    @Inject
    public ShotZoomPresenter(
            ShotZoomInteractor shotZoomInteractor,
            SchedulersProvider schedulersProvider,
            @Named("shot_title") String shotTitle,
            @Named("shot_url") String shotUrl,
            @Named("image_url") String imageUrl
    ) {
        this.shotZoomInteractor = shotZoomInteractor;
        this.permissionsManagerHolder = new PermissionsManagerHolder();
        this.schedulersProvider = schedulersProvider;
        this.shotTitle = shotTitle;
        this.shotUrl = shotUrl;
        this.imageUrl = imageUrl;
    }

    public void setPermissionsManager(PermissionsManager permissionsManager) {
        permissionsManagerHolder.setPermissionsManager(permissionsManager);
    }

    public void removePermissionsManager() {
        permissionsManagerHolder.removePermissionsManager();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        showShot();
    }

    private void showShot() {
        getViewState().showLoadingProgress();
        getViewState().showShotImage(imageUrl);
    }

    public void onImageLoadSuccess() {
        getViewState().hideLoadingProgress();
    }

    public void onImageLoadError() {
        getViewState().hideLoadingProgress();
        getViewState().showErrorLayout();
    }

    public void onDownloadImageClicked() {
        if (permissionsManagerHolder.checkPermissionGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage();
        } else {
            permissionsManagerHolder.requestPermission(Permission.READ_EXTERNAL_STORAGE, permissionResult -> {
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
                .observeOn(schedulersProvider.ui())
                .subscribe(() -> getViewState().showImageSavedMessage(), DebugUtils::showDebugErrorMessage);
    }

    public void onOpenInBrowserClicked() {
        getViewState().openInBrowser(shotUrl);
    }

    public void onShareShotClicked() {
        getViewState().showShotSharing(shotTitle, shotUrl);
    }

}
