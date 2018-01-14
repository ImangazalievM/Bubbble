package com.imangazalievm.bubbble.presentation.mvp.userprofile;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.userprofile.UserFollowersInteractor;
import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class UserFollowersPresenter extends MvpPresenter<UserFollowersView> {

    private static final int PAGE_SIZE = 20;


    private UserFollowersInteractor userFollowersInteractor;
    private SchedulersProvider schedulersProvider;
    private long userId;
    private int currentMaxPage = 1;
    private List<Follow> followers = new ArrayList<>();
    private boolean isFollowersLoading = false;

    @Inject
    public UserFollowersPresenter(UserFollowersInteractor userFollowersInteractor,
                                  SchedulersProvider schedulersProvider,
                                  long userId) {
        this.userFollowersInteractor = userFollowersInteractor;
        this.schedulersProvider = schedulersProvider;
        this.userId = userId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showFollowersLoadingProgress();
        loadMoreFollowers(currentMaxPage);
    }

    private void loadMoreFollowers(int page) {
        isFollowersLoading = true;
        UserFollowersRequestParams requestParams = new UserFollowersRequestParams(userId, page, PAGE_SIZE);
        userFollowersInteractor.getUserFollowers(requestParams)
                .observeOn(schedulersProvider.mainThread())
                .subscribe(this::onFollowersLoaded, this::onFollowersLoadError);
    }

    private void onFollowersLoaded(List<Follow> newFollowers) {
        isFollowersLoading = false;
        if (isFirstLoading()) {
            getViewState().hideFollowersLoadingProgress();
        } else {
            getViewState().hideFollowersLoadingMoreProgress();
        }

        followers.addAll(newFollowers);
        getViewState().showNewFollowers(newFollowers);
    }

    private void onFollowersLoadError(Throwable throwable) {
        isFollowersLoading = true;
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

    public void onLoadMoreFollowersRequest() {
        if (isFollowersLoading) {
            return;
        }

        getViewState().showFollowersLoadingMoreProgress();
        currentMaxPage++;
        loadMoreFollowers(currentMaxPage);
    }

    public void retryLoading() {
        if (isFirstLoading()) {
            getViewState().hideNoNetworkLayout();
            getViewState().showFollowersLoadingProgress();
        } else {
            getViewState().showFollowersLoadingMoreProgress();
        }

        loadMoreFollowers(currentMaxPage);
    }

    public void onFollowerClick(int position) {
        System.out.println(followers.size());
        System.out.println(followers.get(position));
        System.out.println(followers.get(position).getFollower());
        System.out.println(followers.get(position).getFollower().getId());
        getViewState().openUserDetailsScreen(followers.get(position).getFollower().getId());
    }


}
