package com.bubbble.shots.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.SearchQueryListener
import com.bubbble.shots.R
import com.bubbble.shots.databinding.ActivityMainBinding
import com.bubbble.shots.shotslist.ShotsFragment.Companion.newInstance
import com.mikepenz.materialdrawer.DrawerBuilder
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseMvpActivity(), MainView {

    override val layoutRes: Int = R.layout.activity_main

    private val binding: ActivityMainBinding by viewBinding()

    @Inject
    lateinit var presenterFactory: MainPresenter.Factory

    val presenter by moxyPresenter {
        presenterFactory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.setTitle(R.string.app_name)
        binding.toolbar.inflateMenu(R.menu.main)
        initOptionsMenu(binding.toolbar.menu)

        setupViewPager()
        binding.mainPagerTabs.setupWithViewPager(binding.shotsPager)
    }

    private fun initOptionsMenu(menu: Menu) {
        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchQueryListener() {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.onSearchQuery(query)
                return true
            }
        })
    }

    private fun initDrawer() {
        DrawerBuilder()
            .withActivity(this)
            .withToolbar(binding.toolbar)
            .build()
    }

    private fun setupViewPager() {
        val shotsPagerAdapter = ShotsPagerAdapter(supportFragmentManager)
        shotsPagerAdapter.addFragment(
            newInstance(ShotsFeedParams.Sorting.POPULAR),
            resources.getString(R.string.popular)
        )
        shotsPagerAdapter.addFragment(
            newInstance(ShotsFeedParams.Sorting.RECENT),
            resources.getString(R.string.recently)
        )
        binding.shotsPager.adapter = shotsPagerAdapter
    }

}