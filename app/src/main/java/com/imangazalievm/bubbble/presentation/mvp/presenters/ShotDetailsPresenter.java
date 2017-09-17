package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.presentation.commons.permissions.Permission;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManagerHolder;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotDetailsView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShotDetailsPresenter extends MvpPresenter<ShotDetailsView> {

    private static final int COMMENTS_PAGE_SIZE = 20;

    private ShotDetailsInteractor shotDetailsInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private PermissionsManagerHolder permissionsManagerHolder;
    private long shotId;
    private Shot shot;
    private int currentMaxCommentsPage = 1;
    private boolean isCommentsLoading = false;

    @Inject
    public ShotDetailsPresenter(ShotDetailsInteractor shotDetailsInteractor,
                                PermissionsManagerHolder permissionsManagerHolder,
                                RxSchedulersProvider rxSchedulersProvider,
                                long shotId) {
        this.shotDetailsInteractor = shotDetailsInteractor;
        this.permissionsManagerHolder = permissionsManagerHolder;
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.shotId = shotId;
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

        loadShot();
    }

    private void loadShot() {
        getViewState().showLoadingProgress();
        shotDetailsInteractor.getShot(shotId)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onShotLoaded, this::onShotLoadError);
    }

    private void onShotLoaded(Shot shot) {
        this.shot = shot;
        getViewState().hideLoadingProgress();
        getViewState().showShot(shot);

        if (shot.getCommentsCount() > 0) {
            loadMoreComments(1);
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
        loadShot();
    }

    private void loadMoreComments(int page) {
        isCommentsLoading = true;
        ShotCommentsRequestParams shotCommentsRequestParams = new ShotCommentsRequestParams(shotId, page, COMMENTS_PAGE_SIZE);
        shotDetailsInteractor.getShotComments(shotCommentsRequestParams)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onCommentsLoaded, DebugUtils::showDebugErrorMessage);
    }

    private void onCommentsLoaded(List<Comment> newComments) {
        isCommentsLoading = false;
        getViewState().hideCommentsLoadingProgress();
        getViewState().showNewComments(newComments);
    }


    public void onLoadMoreCommentsRequest() {
        if (isCommentsLoading) {
            return;
        }
        currentMaxCommentsPage++;
        loadMoreComments(currentMaxCommentsPage);
    }

    public void onImageClicked() {
        getViewState().openShotImageScreen(shot);
    }

    public void onLikeShotClicked() {

    }

    public void onShareShotClicked() {
        getViewState().showShotSharing(shot.getTitle(), shot.getHtmlUrl());
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
        shotDetailsInteractor.saveImage(shot.getImages().best())
                .compose(rxSchedulersProvider.getIoToMainTransformerCompletableCompletable())
                .subscribe(() -> getViewState().showImageSavedMessage(), DebugUtils::showDebugErrorMessage);
    }

    public void onOpenShotInBrowserClicked() {
        getViewState().openInBrowser(shot.getHtmlUrl());
    }

    public void onShotAuthorProfileClicked() {
        getViewState().openUserProfileScreen(shot.getUser().getId());
    }

    public void onCommentAuthorClick(long userId) {
        getViewState().openUserProfileScreen(userId);
    }

    public void onLinkClicked(String url) {
        getViewState().openInBrowser(url);
    }

    public void onTagClicked(String tag) {

    }

}
