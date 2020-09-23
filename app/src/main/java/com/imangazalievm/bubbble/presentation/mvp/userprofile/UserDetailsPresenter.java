package com.imangazalievm.bubbble.presentation.mvp.userprofile;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.userprofile.UserDetailsInteractor;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserDetailsPresenter extends MvpPresenter<UserDetailsView> {

    private UserDetailsInteractor userDetailsInteractor;
    private SchedulersProvider schedulersProvider;
    private long userId;
    private User user;

    @Inject
    public UserDetailsPresenter(
            UserDetailsInteractor userDetailsInteractor,
            SchedulersProvider schedulersProvider,
            long userId
    ) {
        this.userDetailsInteractor = userDetailsInteractor;
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
        userDetailsInteractor.getUser(userId)
                .observeOn(schedulersProvider.ui())
                .subscribe(this::onUserLoadSuccess, this::onUserLoadError);
    }

    private void onUserLoadSuccess(User user) {
        this.user = user;
        getViewState().hideLoadingProgress();
        getViewState().showUserInfo(user);
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

    public void onUserTwitterButtonClicked() {
        getViewState().openInBrowser(user.getLinks().getTwitter());
    }

    public void onUserWebsiteButtonClicked() {
        getViewState().openInBrowser(user.getLinks().getWeb());
    }

}
