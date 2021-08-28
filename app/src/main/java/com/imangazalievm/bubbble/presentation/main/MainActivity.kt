package com.imangazalievm.bubbble.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.tabs.TabLayout
import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.R
import com.imangazalievm.bubbble.presentation.global.ui.base.MvpAppCompatActivity
import com.imangazalievm.bubbble.presentation.global.ui.commons.SearchQueryListener
import com.imangazalievm.bubbble.presentation.shotslist.ShotsFragment.Companion.newInstance
import com.imangazalievm.bubbble.presentation.shotssearch.ShotsSearchActivity.Companion.buildIntent
import com.mikepenz.materialdrawer.DrawerBuilder

class MainActivity : MvpAppCompatActivity(), MainView {

    private val shotsViewPager: ViewPager by lazy {
        findViewById(R.id.shots_pager)
    }
    private val tabLayout: TabLayout by lazy {
        findViewById(R.id.tabs)
    }

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = initToolbar()
        initDrawer(toolbar)
        initViews()
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.main)
        initOptionsMenu(toolbar.menu)
        return toolbar
    }

    fun initOptionsMenu(menu: Menu) {
        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchQueryListener() {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainPresenter.onSearchQuery(query)
                return true
            }
        })
    }

    private fun initDrawer(toolbar: Toolbar) {
        DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .build()
    }

    private fun initViews() {
        setupViewPager(shotsViewPager)
        tabLayout.setupWithViewPager(shotsViewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val shotsPagerAdapter = ShotsPagerAdapter(supportFragmentManager)
        shotsPagerAdapter.addFragment(
            newInstance(Constants.SHOTS_SORT_POPULAR),
            resources.getString(R.string.popular)
        )
        shotsPagerAdapter.addFragment(
            newInstance(Constants.SHOTS_SORT_RECENT),
            resources.getString(R.string.recently)
        )
        viewPager.adapter = shotsPagerAdapter
    }

    override fun openSearchScreen(searchQuery: String) {
        startActivity(buildIntent(this, searchQuery))
    }
}