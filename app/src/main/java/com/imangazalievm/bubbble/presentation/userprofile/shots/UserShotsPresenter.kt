package com.imangazalievm.bubbble.presentation.userprofile.shots;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.userprofile.UserShotsInteractor;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class UserShotsPresenter extends MvpPresenter<UserShotsView> {

    private static final int PAGE_SIZE = 20;


    private UserShotsInteractor userShotsInteractor;
    private SchedulersProvider schedulersProvider;
    private long userId;
    private int currentMaxPage = 1;
    private List<Shot> shots = new ArrayList<>();
    private boolean isShotsLoading = false;

    @Inject
    public UserShotsPresenter(UserShotsInteractor userShotsInteractor,
                              SchedulersProvider schedulersProvider,
                              long userId) {
        this.userShotsInteractor = userShotsInteractor;
        this.schedulersProvider = schedulersProvider;
        this.userId = userId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showShotsLoadingProgress();
        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        UserShotsRequestParams userShotsRequestParams = new UserShotsRequestParams(userId, page, PAGE_SIZE);
        userShotsInteractor.getUserShots(userShotsRequestParams)
                .observeOn(schedulersProvider.ui())
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
                getViewState().showLoadMoreError();
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

        getViewState().showShotsLoadingMoreProgress();
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
