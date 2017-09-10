package com.imangazalievm.bubbble.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerUserFollowersPresenterComponent;
import com.imangazalievm.bubbble.di.UserFollowersPresenterComponent;
import com.imangazalievm.bubbble.di.modules.UserFollowersPresenterModule;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserFollowersPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.UserFollowersView;
import com.imangazalievm.bubbble.presentation.ui.activities.UserProfileActivity;
import com.imangazalievm.bubbble.presentation.ui.adapters.UserFollowersAdapter;
import com.imangazalievm.bubbble.presentation.ui.commons.EndlessRecyclerOnScrollListener;

import java.util.List;

public class UserFollowersFragment extends MvpAppCompatFragment implements UserFollowersView {

    private static final String USER_ID_ARG = "user_id";

    public static UserFollowersFragment newInstance(long userId) {
        UserFollowersFragment fragment = new UserFollowersFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID_ARG, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    UserFollowersPresenter userFollowersPresenter;

    @ProvidePresenter
    UserFollowersPresenter providePresenter() {
        long userId = getArguments().getLong(USER_ID_ARG);

        UserFollowersPresenterComponent presenterComponent = DaggerUserFollowersPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .userFollowersPresenterModule(new UserFollowersPresenterModule(userId))
                .build();

        return presenterComponent.getPresenter();
    }

    private View snackBarContainer;
    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView followersRecyclerView;
    private UserFollowersAdapter userFollowersAdapter;
    private LinearLayoutManager followersListLayoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shots, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        snackBarContainer = view.findViewById(R.id.snack_bar_container);

        loadingLayout = view.findViewById(R.id.loading_layout);
        noNetworkLayout = view.findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> userFollowersPresenter.retryLoading());

        followersRecyclerView = view.findViewById(R.id.shotsRecyclerView);
        followersListLayoutManager = new LinearLayoutManager(getContext());
        followersRecyclerView.setLayoutManager(followersListLayoutManager);

        userFollowersAdapter = new UserFollowersAdapter(getContext());
        followersRecyclerView.setAdapter(userFollowersAdapter);
        userFollowersAdapter.setOnItemClickListener(position -> userFollowersPresenter.onFollowerClick(position));

        initLoadMoreScrollListener();
        followersRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void initLoadMoreScrollListener() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(followersListLayoutManager) {
            @Override
            public void onLoadMore() {
                userFollowersPresenter.onLoadMoreFollowersRequest();
            }
        };
    }

    @Override
    public void showNewFollowers(List<Follow> newFollowers) {
        followersRecyclerView.setVisibility(View.VISIBLE);
        userFollowersAdapter.addItems(newFollowers);
    }

    @Override
    public void showFollowersLoadingProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFollowersLoadingProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showFollowersLoadingMoreProgress() {
        userFollowersAdapter.showLoadingIndicator();
    }

    @Override
    public void hideFollowersLoadingMoreProgress() {
        userFollowersAdapter.hideLoadingIndicator();
    }

    @Override
    public void openUserDetailsScreen(long userId) {
        startActivity(UserProfileActivity.buildIntent(getActivity(), userId));
    }

    @Override
    public void showNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError() {
        userFollowersAdapter.setLoadingError(true);
    }

}
