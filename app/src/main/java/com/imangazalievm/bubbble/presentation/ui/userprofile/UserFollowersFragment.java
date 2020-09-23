package com.imangazalievm.bubbble.presentation.ui.userprofile;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.userprofile.DaggerUserFollowersPresenterComponent;
import com.imangazalievm.bubbble.di.userprofile.UserFollowersPresenterComponent;
import com.imangazalievm.bubbble.di.userprofile.UserFollowersPresenterModule;
import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserFollowersPresenter;
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserFollowersView;
import com.imangazalievm.bubbble.presentation.ui.global.adapters.UserFollowersAdapter;
import com.imangazalievm.bubbble.presentation.ui.global.base.MvpAppCompatFragment;
import com.imangazalievm.bubbble.presentation.ui.global.commons.EndlessRecyclerOnScrollListener;

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

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView followersRecyclerView;
    private UserFollowersAdapter userFollowersAdapter;
    private LinearLayoutManager followersListLayoutManager;

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
        loadingLayout = view.findViewById(R.id.loading_layout);
        noNetworkLayout = view.findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> userFollowersPresenter.retryLoading());

        followersRecyclerView = view.findViewById(R.id.shotsRecyclerView);
        followersListLayoutManager = new LinearLayoutManager(getContext());
        followersRecyclerView.setLayoutManager(followersListLayoutManager);

        userFollowersAdapter = new UserFollowersAdapter(getContext());
        followersRecyclerView.setAdapter(userFollowersAdapter);
        userFollowersAdapter.setOnItemClickListener(position -> userFollowersPresenter.onFollowerClick(position));

        followersRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(followersListLayoutManager) {
            @Override
            public void onLoadMore() {
                userFollowersPresenter.onLoadMoreFollowersRequest();
            }
        });
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
