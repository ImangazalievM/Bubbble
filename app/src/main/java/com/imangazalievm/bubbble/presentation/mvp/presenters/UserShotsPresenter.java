package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.di.UserShotsPresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserShotsInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.UserShotsView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class UserShotsPresenter extends MvpPresenter<UserShotsView> {

    private static final int PAGE_SIZE = 20;


    private UserShotsInteractor userShotsInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private long userId;
    private int currentMaxPage = 1;
    private List<Shot> shots = new ArrayList<>();
    private boolean isShotsLoading = false;

    @Inject
    public UserShotsPresenter(UserShotsInteractor userShotsInteractor, RxSchedulersProvider rxSchedulersProvider, long userId) {
        this.userShotsInteractor = userShotsInteractor;
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.userId = userId;

        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        getViewState().showShotsLoadingMoreProgress();
        UserShotsRequestParams userShotsRequestParams = new UserShotsRequestParams(userId, page, PAGE_SIZE);
        userShotsInteractor.getUserShots(userShotsRequestParams)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onShotsLoaded, this::onShotsLoadError);
    }

    private void onShotsLoaded(List<Shot> newShots) {
        isShotsLoading = false;
        if (isFirstLoading()) {
            getViewState().hideShotsLoadingProgress();
        } else {
            getViewState().hideShotsLoadingMoreProgress();
        }

        shots.addAll(newShots);
        getViewState().showNewShots(newShots);
    }

    private void onShotsLoadError(Throwable throwable) {
        isShotsLoading = true;
        if (throwable instanceof NoNetworkException) {
            if (isFirstLoading()) {
                getViewState().hideShotsLoadingProgress();
                getViewState().showNoNetworkLayout();
            } else {
                getViewState().hideShotsLoadingMoreProgress();
                getViewState().showNoNetworkMessage();
            }
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    private boolean isFirstLoading() {
        return currentMaxPage == 1;
    }

    public void onLoadMoreShotsRequest() {
        if (isShotsLoading) {
            return;
        }
        currentMaxPage++;
        loadMoreShots(currentMaxPage);
    }

    public void retryLoading() {
        if (isFirstLoading()) {
            getViewState().hideNoNetworkLayout();
            getViewState().showShotsLoadingProgress();
        } else {
            getViewState().showShotsLoadingMoreProgress();
        }

        loadMoreShots(currentMaxPage);
    }

    public void onShotClick(int position) {
        getViewState().openShotDetailsScreen(shots.get(position).getId());
    }


}
