package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.di.UserFollowersPresenterComponent;
import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.UserFollowersInteractor;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.views.UserFollowersView;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class UserFollowersPresenter extends MvpPresenter<UserFollowersView> {

    private static final int PAGE_SIZE = 20;

    @Inject
    UserFollowersInteractor userFollowersInteractor;

    private long userId;
    private int currentMaxPage = 1;
    private List<Follow> followers = new ArrayList<>();
    private boolean isShotsLoading = false;

    public UserFollowersPresenter(UserFollowersPresenterComponent presenterComponent, long userId) {
        this.userId = userId;
        presenterComponent.inject(this);

        loadMoreShots(currentMaxPage);
    }

    private void loadMoreShots(int page) {
        isShotsLoading = true;
        getViewState().showFollowersLoadingMoreProgress();
        UserFollowersRequestParams requestParams = new UserFollowersRequestParams(userId, page, PAGE_SIZE);
        userFollowersInteractor.getUserFollowers(requestParams)
                .subscribe(this::onFollowerssLoaded, this::onShotsLoadError);
    }

    private void onFollowerssLoaded(List<Follow> newFollowers) {
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
            getViewState().showFollowersLoadingProgress();
        } else {
            getViewState().showFollowersLoadingMoreProgress();
        }

        loadMoreShots(currentMaxPage);
    }

    public void onShotClick(int position) {
        getViewState().openUserDetailsScreen(followers.get(position).getUser().getId());
    }


}
