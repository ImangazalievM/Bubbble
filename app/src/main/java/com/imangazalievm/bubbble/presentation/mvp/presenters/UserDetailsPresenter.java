package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.UserDetailsView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class UserDetailsPresenter extends MvpPresenter<UserDetailsView> {

    private UserDetailsInteractor userDetailsInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private long userId;
    private User user;

    @Inject
    public UserDetailsPresenter(UserDetailsInteractor userDetailsInteractor,
                                RxSchedulersProvider rxSchedulersProvider,
                                long userId) {
        this.userDetailsInteractor = userDetailsInteractor;
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.userId = userId;

        loadUser();
    }

    private void loadUser() {
        userDetailsInteractor.getUser(userId)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
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

    public void onUserTwitterButtonClicked() {
        getViewState().openInBrowser(user.getLinks().getTwitter());
    }

    public void onUserWebsiteButtonClicked() {
        getViewState().openInBrowser(user.getLinks().getWeb());
    }

}
