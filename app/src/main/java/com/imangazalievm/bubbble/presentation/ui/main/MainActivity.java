package com.imangazalievm.bubbble.presentation.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.tabs.TabLayout;
import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.presentation.mvp.main.MainPresenter;
import com.imangazalievm.bubbble.presentation.mvp.main.MainView;
import com.imangazalievm.bubbble.presentation.ui.global.base.MvpAppCompatActivity;
import com.imangazalievm.bubbble.presentation.ui.global.commons.SearchQueryListener;
import com.imangazalievm.bubbble.presentation.ui.shotslist.ShotsFragment;
import com.imangazalievm.bubbble.presentation.ui.shotssearch.ShotsSearchActivity;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private ViewPager shotsViewPager;
    private TabLayout tabLayout;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initViews();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main);
        initOptionsMenu(toolbar.getMenu());
        return toolbar;
    }

    public void initOptionsMenu(Menu menu) {
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchQueryListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.onSearchQuery(query);
                return true;
            }
        });
    }

    private void initDrawer(Toolbar toolbar) {
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .build();
    }

    private void initViews() {
        shotsViewPager = (ViewPager) findViewById(R.id.shots_pager);
        setupViewPager(shotsViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(shotsViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ShotsPagerAdapter shotsPagerAdapter = new ShotsPagerAdapter(getSupportFragmentManager());
        shotsPagerAdapter.addFragment(ShotsFragment.newInstance(Constants.SHOTS_SORT_POPULAR), getResources().getString(R.string.popular));
        shotsPagerAdapter.addFragment(ShotsFragment.newInstance(Constants.SHOTS_SORT_RECENT), getResources().getString(R.string.recently));
        viewPager.setAdapter(shotsPagerAdapter);
    }

    @Override
    public void openSearchScreen(String searchQuery) {
        startActivity(ShotsSearchActivity.buildIntent(this, searchQuery));
    }
}