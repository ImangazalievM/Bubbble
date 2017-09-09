package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserFollowersInteractor;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.presentation.commons.rx.RxSchedulersProvider;
import com.imangazalievm.bubbble.presentation.mvp.views.UserFollowersView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class UserFollowersPresenter extends MvpPresenter<UserFollowersView> {

    private static final int PAGE_SIZE = 20;


    private UserFollowersInteractor userFollowersInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private long userId;
    private int currentMaxPage = 1;
    private List<Follow> followers = new ArrayList<>();
    private boolean isShotsLoading = false;

    @Inject
    public UserFollowersPresenter(UserFollowersInteractor userFollowersInteractor,
                                  RxSchedulersProvider rxSchedulersProvider,
                                  long userId) {
        this.userFollowersInteractor = userFollowersInteractor;
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.userId = userId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showFollowersLoadingProgress();
        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        UserFollowersRequestParams requestParams = new UserFollowersRequestParams(userId, page, PAGE_SIZE);
        userFollowersInteractor.getUserFollowers(requestParams)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onFollowersLoaded, this::onShotsLoadError);
    }

    private void onFollowersLoaded(List<Follow> newFollowers) {
        isShotsLoading = false;
        if (isFirstLoading()) {
            getViewState().hideFollowersLoadingProgress();
        } else {
            getViewState().hideFollowersLoadingMoreProgress();
        }

        followers.addAll(newFollowers);
        getViewState().showNewFollowers(newFollowers);
    }

    private void onShotsLoadError(Throwable throwable) {
        isShotsLoading = true;
        if (throwable instanceof NoNetworkException) {
            if (isFirstLoading()) {
                getViewState().hideFollowersLoadingProgress();
                getViewState().showNoNetworkLayout();
            } else {
                getViewState().hideFollowersLoadingMoreProgress();
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

        getViewState().showFollowersLoadingMoreProgress();
        currentMaxPage++;
        loadMoreShots(currentMaxPage);
    }

    public void retryLoading() {
        if (isFirstLoading()) {
            getViewState().hideNoNetworkLayout();
            getViewState().showFollowersLoadingProgress();
        } else {
            getViewState().showFollowersLoadingMoreProgress();
        }

        loadMoreShots(currentMaxPage);
    }

    public void onFollowerClick(int position) {
        getViewState().openUserDetailsScreen(followers.get(position).getFollower().getId());
    }


}
