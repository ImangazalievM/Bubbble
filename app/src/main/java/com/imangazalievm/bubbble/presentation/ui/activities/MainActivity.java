package com.imangazalievm.bubbble.presentation.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.R;
import com.imangazalievm.bubbble.presentation.ui.adapters.ShotsPagerAdapter;
import com.imangazalievm.bubbble.presentation.ui.fragments.ShotsFragment;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class MainActivity extends BaseMvpActivity {

    private ViewPager shotsViewPager;
    private TabLayout tabLayout;

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
        setSupportActionBar(toolbar);
        return toolbar;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}