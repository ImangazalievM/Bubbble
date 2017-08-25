package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.di.ShotDetailPresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.commons.Permission;
import com.imangazalievm.bubbble.presentation.mvp.commons.PermissionsManager;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotDetailView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class ShotDetailsPresenter extends MvpPresenter<ShotDetailView> {

    private static final int COMMENTS_PAGE_SIZE = 20;

    @Inject
    ShotDetailsInteractor shotDetailsInteractor;

    private PermissionsManager permissionsManager;
    private long shotId;
    private int currentMaxCommentsPage = 1;
    private Shot shot;

    public ShotDetailsPresenter(ShotDetailPresenterComponent presenterComponent,
                                PermissionsManager permissionsManager,
                                long shotId) {
        this.permissionsManager = permissionsManager;
        presenterComponent.inject(this);

        this.shotId = shotId;

        loadShot();
    }

    private void loadShot() {
        shotDetailsInteractor.getShot(shotId)
                .subscribe(this::onShotLoaded, this::onShotLoadError);
    }

    private void onShotLoaded(Shot shot) {
        this.shot = shot;
        getViewState().hideLoadingProgress();
        getViewState().showShot(shot);

        if (shot.getCommentsCount() > 0) {
            loadMoreComments(0);
        } else {
            getViewState().showNoComments();
        }
    }

    private void onShotLoadError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            getViewState().hideLoadingProgress();
            getViewState().showNoNetworkLayout();
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    public void onImageLoadError() {
        getViewState().hideImageLoadingProgress();
    }

    public void onImageLoadSuccess() {
        getViewState().hideImageLoadingProgress();
    }

    public void retryLoading() {
        getViewState().hideNoNetworkLayout();
        getViewState().showLoadingProgress();
        loadShot();
    }

    private void loadMoreComments(int page) {
        currentMaxCommentsPage++;
        ShotCommentsRequestParams shotCommentsRequestParams = new ShotCommentsRequestParams(shotId, page, COMMENTS_PAGE_SIZE);
        shotDetailsInteractor.getShotComments(shotCommentsRequestParams)
                .subscribe(newComments -> {
                    getViewState().hideCommentsLoadingProgress();
                    getViewState().showNewComments(newComments);
                }, DebugUtils::showDebugErrorMessage);
    }


    public void onLoadMoreCommentsRequest() {

    }

    public void onImageClick() {
        getViewState().openShotImageScreen(shot);
    }

    public void onLikeShotClick() {

    }

    public void onShareShotClick() {
        getViewState().showShotSharing(shot.getTitle(), shot.getHtmlUrl());
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
        shotDetailsInteractor.saveImage(shot.getImages().best())
                .subscribe(() -> getViewState().showImageSavedMessage(), DebugUtils::showDebugErrorMessage);
    }

    public void onOpenInBrowserClicked() {
        getViewState().openInBrowser(shot.getHtmlUrl());
    }

    public void onShotAuthorProfileClick() {
        getViewState().openUserProfileScreen(shot.getUser().getId());
    }

    public void onUserSelected(long userId) {
        getViewState().openUserProfileScreen(userId);
    }

    public void onLinkClicked(String url) {
        getViewState().openInBrowser(url);
    }

    public void onTagClicked(String tag) {

    }

}
