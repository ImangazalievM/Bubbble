package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserProfileInteractor;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.UserProfileView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {


    private UserProfileInteractor userProfileInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private long userId;
    private User user;

    @Inject
    public UserProfilePresenter(UserProfileInteractor userProfileInteractor, RxSchedulersProvider rxSchedulersProvider, long userId) {
        this.userProfileInteractor = userProfileInteractor;
        this.rxSchedulersProvider = rxSchedulersProvider;
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
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
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
