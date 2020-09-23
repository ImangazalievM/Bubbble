package com.imangazalievm.bubbble.presentation.mvp.shotssearch;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.shotssearch.ShotsSearchInteractor;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ShotsSearchPresenter extends MvpPresenter<ShotsSearchView> {

    private static final int PAGE_SIZE = 20;
    private static final int MAX_PAGE_NUMBER = 25;

    private ShotsSearchInteractor shotsSearchInteractor;
    private SchedulersProvider schedulersProvider;
    private String searchQuery;
    private String sort;
    private List<Shot> shots = new ArrayList<>();
    private int currentMaxPage = 1;
    private boolean isShotsLoading = false;

    @Inject
    public ShotsSearchPresenter(
            ShotsSearchInteractor shotsSearchInteractor,
            SchedulersProvider schedulersProvider,
            String searchQuery
    ) {
        this.shotsSearchInteractor = shotsSearchInteractor;
        this.schedulersProvider = schedulersProvider;
        this.searchQuery = searchQuery;
        this.sort = Constants.SHOTS_SORT_POPULAR;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();


        startSearch(searchQuery);
    }

    private void startSearch(String searchQuery) {
        this.searchQuery = searchQuery;
        getViewState().showShotsLoadingProgress();
        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        ShotsSearchRequestParams shotsRequestParams = new ShotsSearchRequestParams(searchQuery, sort, page, PAGE_SIZE);
        shotsSearchInteractor.search(shotsRequestParams)
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

    public void onNewSearchQuery(String searchQuery) {
        getViewState().clearShotsList();
        shots.clear();
        startSearch(searchQuery);
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

    private boolean isFirstLoading() {
        return currentMaxPage == 1;
    }

    public void onLoadMoreShotsRequest() {
        if (isShotsLoading) {
            return;
        }

        if (currentMaxPage < MAX_PAGE_NUMBER) {
            getViewState().showShotsLoadingMoreProgress();
            currentMaxPage++;
            loadMoreShots(currentMaxPage);
        }
    }

    public void onShotClick(int position) {
        getViewState().openShotDetailsScreen(shots.get(position).getId());
    }

}
