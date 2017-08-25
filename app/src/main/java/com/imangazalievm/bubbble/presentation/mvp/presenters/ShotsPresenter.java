package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.di.ShotsPresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotsInteractor;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShotsPresenter extends MvpPresenter<ShotsView> {

    private static final int PAGE_SIZE = 20;
    private static final int MAX_PAGE_NUMBER = 25;

    @Inject
    ShotsInteractor shotsInteractor;

    private String shotsSort;
    private int currentMaxPage = 1;
    private List<Shot> shots = new ArrayList<>();
    private boolean isShotsLoading = false;

    public ShotsPresenter(ShotsPresenterComponent presenterComponent, String shotsSort) {
        this.shotsSort = shotsSort;
        presenterComponent.inject(this);

        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        getViewState().showShotsLoadingMoreProgress();
        ShotsRequestParams shotsRequestParams = new ShotsRequestParams(shotsSort, page, PAGE_SIZE);
        shotsInteractor.getShots(shotsRequestParams)
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
        if (currentMaxPage < MAX_PAGE_NUMBER) {
            currentMaxPage++;
            loadMoreShots(currentMaxPage);
        }
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
