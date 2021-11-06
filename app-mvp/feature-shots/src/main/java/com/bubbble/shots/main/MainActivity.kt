package com.bubbble.shots.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import moxy.presenter.InjectPresenter
import com.bubbble.core.models.shot.ShotsParams
import com.google.android.material.tabs.TabLayout
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.SearchQueryListener
import com.bubbble.shots.R
import com.bubbble.shots.shotslist.ShotsFragment.Companion.newInstance
import com.mikepenz.materialdrawer.DrawerBuilder
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : BaseMvpActivity(), MainView {

    override val layoutRes: Int = R.layout.activity_main

    private val shotsViewPager: ViewPager by lazy {
        findViewById(R.id.shots_pager)
    }
    private val tabLayout: TabLayout by lazy {
        findViewById(R.id.tabs)
    }

    @Inject
    lateinit var presenterFactory: MainPresenter.Factory

    val presenter by moxyPresenter {
        presenterFactory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                presenter.onSearchQuery(query)
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
            newInstance(ShotsParams.Sorting.POPULAR),
            resources.getString(R.string.popular)
        )
        shotsPagerAdapter.addFragment(
            newInstance(ShotsParams.Sorting.RECENT),
            resources.getString(R.string.recently)
        )
        viewPager.adapter = shotsPagerAdapter
    }

    override fun openSearchScreen(searchQuery: String) {
        //ToDo: startActivity(buildIntent(this, searchQuery))
    }

}