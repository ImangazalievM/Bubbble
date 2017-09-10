package com.imangazalievm.bubbble.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.di.DaggerShotsSearchPresenterComponent;
import com.imangazalievm.bubbble.di.ShotsSearchPresenterComponent;
import com.imangazalievm.bubbble.di.modules.ShotsSearchPresenterModule;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.presentation.mvp.presenters.ShotsSearchPresenter;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotsSearchView;
import com.imangazalievm.bubbble.presentation.ui.adapters.ShotsAdapter;
import com.imangazalievm.bubbble.presentation.ui.commons.EndlessRecyclerOnScrollListener;
import com.imangazalievm.bubbble.presentation.ui.commons.SearchQueryListener;

import java.util.List;

public class ShotsSearchActivity extends BaseMvpActivity implements ShotsSearchView {

    private static final String KEY_SEARCH_QUERY = "key_search_query";

    public static Intent buildIntent(Context context, String searchQuery) {
        Intent intent = new Intent(context, ShotsSearchActivity.class);
        intent.putExtra(KEY_SEARCH_QUERY, searchQuery);
        return intent;
    }

    @InjectPresenter
    ShotsSearchPresenter shotsSearchPresenter;

    @ProvidePresenter
    ShotsSearchPresenter providePresenter() {
        String searchQuery = getIntent().getStringExtra(KEY_SEARCH_QUERY);

        ShotsSearchPresenterComponent shotsPresenterComponent = DaggerShotsSearchPresenterComponent.builder()
                .applicationComponent(BubbbleApplication.getComponent())
                .shotsSearchPresenterModule(new ShotsSearchPresenterModule(searchQuery))
                .build();
        return shotsPresenterComponent.getPresenter();
    }

    private CoordinatorLayout shotDetailContainer;
    private Toolbar toolbar;

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView shotsRecyclerView;
    private ShotsAdapter shotsAdapter;
    private LinearLayoutManager shotsListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_search);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.shots_search);
        initOptionsMenu(toolbar.getMenu());
    }

    public void initOptionsMenu(Menu menu) {
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchQueryListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shotsSearchPresenter.onNewSearchQuery(query);
                return true;
            }
        });
    }

    private void initViews() {
        loadingLayout = findViewById(R.id.loading_layout);
        noNetworkLayout = findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotsSearchPresenter.retryLoading());

        shotsRecyclerView = (RecyclerView) findViewById(R.id.shotsRecyclerView);
        shotsListLayoutManager = new LinearLayoutManager(this);
        shotsRecyclerView.setLayoutManager(shotsListLayoutManager);
        shotsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            @Override
            public void onLoadMore() {
                shotsSearchPresenter.onLoadMoreShotsRequest();
            }
        });

        initRecyclerViewAdapter();
    }

    private void initRecyclerViewAdapter() {
        shotsAdapter = new ShotsAdapter(this);
        shotsAdapter.setOnItemClickListener(position -> shotsSearchPresenter.onShotClick(position));
        shotsAdapter.setOnRetryLoadMoreListener(() -> shotsSearchPresenter.retryLoading());
        shotsRecyclerView.setAdapter(shotsAdapter);
    }

    @Override
    public void showNewShots(List<Shot> newShots) {
        shotsRecyclerView.setVisibility(View.VISIBLE);
        shotsAdapter.addItems(newShots);
    }

    @Override
    public void clearShotsList() {
        initRecyclerViewAdapter();
        shotsRecyclerView.setVisibility(View.GONE);
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
        startActivity(ShotDetailsActivity.buildIntent(this, shotId));
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