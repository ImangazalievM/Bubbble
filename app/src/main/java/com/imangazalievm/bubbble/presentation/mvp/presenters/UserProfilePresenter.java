package com.imangazalievm.bubbble.presentation.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.di.UserProfilePresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserProfileInteractor;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.views.UserProfileView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {

    @Inject
    UserProfileInteractor userProfileInteractor;

    private long userId;
    private User user;

    public UserProfilePresenter(UserProfilePresenterComponent presenterComponent, long userId) {
        presenterComponent.inject(this);

        this.userId = userId;
        loadUser();
    }

    private void loadUser() {
        getViewState().showLoadingProgress();
        userProfileInteractor.getUser(userId)
                .subscribe(this::onUserLoaded, this::onUserLoadError);
    }

    private void onUserLoaded(User user) {
        this.user = user;
        getViewState().hideLoadingProgress();
        getViewState().showUser(user);
    }

    private void onUserLoadError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            getViewState().hideLoadingProgress();
            getViewState().showNoNetworkLayout();
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    public void onShareProfileClicked() {
        getViewState().showUserProfileSharing(user);
    }

    public void onOpenInBrowserClicked() {
        getViewState().openInBrowser(user.getHtmlUrl());
    }

    public void onLinkClicked(String url) {
        getViewState().openInBrowser(url);
    }

    public void onUserSelected(long userId) {
        getViewState().openUserProfileScreen(userId);
    }

    public void retryLoading() {
        getViewState().hideNoNetworkLayout();
        getViewState().showLoadingProgress();
        loadUser();
    }

    public void onBackPressed() {
        getViewState().closeScreen();
    }

}
