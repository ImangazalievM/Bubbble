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
import com.imangazalievm.bubbble.di.DaggerUserShotsPresenterComponent;
import com.imangazalievm.bubbble.di.UserShotsPresenterComponent;
import com.imangazalievm.bubbble.di.modules.UserShotsPresenterModule;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.presentation.mvp.presenters.UserShotsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.UserShotsView;
import com.imangazalievm.bubbble.presentation.ui.activities.ShotDetailsActivity;
import com.imangazalievm.bubbble.presentation.ui.adapters.ShotsAdapter;
import com.imangazalievm.bubbble.presentation.ui.commons.EndlessRecyclerOnScrollListener;

import java.util.List;

public class UserShotsFragment extends MvpAppCompatFragment implements UserShotsView {

    private static final String USER_ID_ARG = "user_id";

    public static UserShotsFragment newInstance(long userId) {
        UserShotsFragment fragment = new UserShotsFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID_ARG, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    UserShotsPresenter userShotsPresenter;

    @ProvidePresenter
    UserShotsPresenter providePresenter() {
        long userId = getArguments().getLong(USER_ID_ARG);

        UserShotsPresenterComponent presenterComponent = DaggerUserShotsPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .userShotsPresenterModule(new UserShotsPresenterModule(userId))
                .build();

        return presenterComponent.getPresenter();
    }

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView shotsRecyclerView;
    private ShotsAdapter shotsAdapter;
    private LinearLayoutManager shotsListLayoutManager;
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
        loadingLayout = view.findViewById(R.id.loading_layout);
        noNetworkLayout = view.findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> userShotsPresenter.retryLoading());

        shotsRecyclerView = view.findViewById(R.id.shotsRecyclerView);
        shotsListLayoutManager = new LinearLayoutManager(getContext());
        shotsRecyclerView.setLayoutManager(shotsListLayoutManager);

        shotsAdapter = new ShotsAdapter(getContext());
        shotsRecyclerView.setAdapter(shotsAdapter);
        shotsAdapter.setOnItemClickListener(position -> userShotsPresenter.onShotClick(position));

        initLoadMoreScrollListener();
        shotsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void initLoadMoreScrollListener() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            @Override
            public void onLoadMore() {
                userShotsPresenter.onLoadMoreShotsRequest();
            }
        };
    }

    @Override
    public void showNewShots(List<Shot> newShots) {
        shotsRecyclerView.setVisibility(View.VISIBLE);
        shotsAdapter.addItems(newShots);
    }

    @Override
    public void showShotsLoadingProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShotsLoadingProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showShotsLoadingMoreProgress() {
        shotsAdapter.setLoadingMore(true);
    }

    @Override
    public void hideShotsLoadingMoreProgress() {
        shotsAdapter.setLoadingMore(false);
    }

    @Override
    public void openShotDetailsScreen(long shotId) {
        startActivity(ShotDetailsActivity.buildIntent(getActivity(), shotId));
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
        shotsAdapter.setLoadingError(true);
    }

}
