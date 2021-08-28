package com.imangazalievm.bubbble.presentation.userprofile.shots;

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
import com.imangazalievm.bubbble.di.userprofile.DaggerUserShotsPresenterComponent;
import com.imangazalievm.bubbble.di.userprofile.UserShotsPresenterComponent;
import com.imangazalievm.bubbble.di.userprofile.UserShotsPresenterModule;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.presentation.global.ui.base.MvpAppCompatFragment;
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsActivity;
import com.imangazalievm.bubbble.presentation.global.ui.adapters.ShotsAdapter;
import com.imangazalievm.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener;

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

        shotsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            @Override
            public void onLoadMore() {
                userShotsPresenter.onLoadMoreShotsRequest();
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
