package com.imangazalievm.bubbble.presentation.userprofile;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.userprofile.UserProfileInteractor;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {


    private UserProfileInteractor userProfileInteractor;
    private SchedulersProvider schedulersProvider;
    private long userId;
    private User user;

    @Inject
    public UserProfilePresenter(UserProfileInteractor userProfileInteractor, SchedulersProvider schedulersProvider, long userId) {
        this.userProfileInteractor = userProfileInteractor;
        this.schedulersProvider = schedulersProvider;
        this.userId = userId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadUser();
    }

    private void loadUser() {
        getViewState().showLoadingProgress();
        userProfileInteractor.getUser(userId)
                .observeOn(schedulersProvider.ui())
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

    public void retryLoading() {
        getViewState().hideNoNetworkLayout();
        loadUser();
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



    public void onBackPressed() {
        getViewState().closeScreen();
    }

}
