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
    private boolean isFollowersLoading = false;

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
        loadMoreFollowers(currentMaxPage);
    }

    private void loadMoreFollowers(int page) {
        isFollowersLoading = true;
        UserFollowersRequestParams requestParams = new UserFollowersRequestParams(userId, page, PAGE_SIZE);
        userFollowersInteractor.getUserFollowers(requestParams)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
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
