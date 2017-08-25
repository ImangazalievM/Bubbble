package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.di.UserDetailsPresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.mvp.views.UserInfoView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserDetailsPresenter extends MvpPresenter<UserInfoView> {

    @Inject
    UserDetailsInteractor userDetailsInteractor;

    private long userId;
    private User user;

    public UserDetailsPresenter(UserDetailsPresenterComponent presenterComponent, long userId) {
        presenterComponent.inject(this);

        this.userId = userId;

        loadUser();
    }

    private void loadUser() {
        userDetailsInteractor.getUser(userId)
                .subscribe(this::onUserLoadSuccess, this::onUserLoadError);
    }

    private void onUserLoadSuccess(User user) {
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
        getViewState().showLoadingProgress();
        loadUser();
    }
}
