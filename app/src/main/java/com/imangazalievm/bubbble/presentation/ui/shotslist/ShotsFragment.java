package com.imangazalievm.bubbble.presentation.ui.shotslist;

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
import com.imangazalievm.bubbble.di.shotslist.DaggerShotsPresenterComponent;
import com.imangazalievm.bubbble.di.shotslist.ShotsPresenterComponent;
import com.imangazalievm.bubbble.di.shotslist.ShotsPresenterModule;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.presentation.mvp.shotslist.ShotsPresenter;
import com.imangazalievm.bubbble.presentation.mvp.shotslist.ShotsView;
import com.imangazalievm.bubbble.presentation.ui.shotdetails.ShotDetailsActivity;
import com.imangazalievm.bubbble.presentation.ui.global.adapters.ShotsAdapter;
import com.imangazalievm.bubbble.presentation.ui.global.commons.EndlessRecyclerOnScrollListener;

import java.util.List;

public class ShotsFragment extends MvpAppCompatFragment implements ShotsView {

    private static final String SORT_TYPE_ARG = "sort";

    public static ShotsFragment newInstance(String sort) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle args = new Bundle();
        args.putString(SORT_TYPE_ARG, sort);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    ShotsPresenter shotsPresenter;

    @ProvidePresenter
    ShotsPresenter providePresenter() {
        String sortType = getArguments().getString(SORT_TYPE_ARG);

        ShotsPresenterComponent shotsPresenterComponent = DaggerShotsPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .shotsPresenterModule(new ShotsPresenterModule(sortType))
                .build();
        return shotsPresenterComponent.getPresenter();
    }

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView shotsRecyclerView;
    private ShotsAdapter shotsAdapter;
    private LinearLayoutManager shotsListLayoutManager;

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
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotsPresenter.retryLoading());

        shotsRecyclerView = view.findViewById(R.id.shotsRecyclerView);
        shotsListLayoutManager = new LinearLayoutManager(getContext());
        shotsRecyclerView.setLayoutManager(shotsListLayoutManager);

        shotsAdapter = new ShotsAdapter(getContext());
        shotsAdapter.setOnItemClickListener(position -> shotsPresenter.onShotClick(position));
        shotsAdapter.setOnRetryLoadMoreListener(() -> shotsPresenter.retryLoading());
        shotsRecyclerView.setAdapter(shotsAdapter);
        shotsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            @Override
            public void onLoadMore() {
                shotsPresenter.onLoadMoreShotsRequest();
            }
        });
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
