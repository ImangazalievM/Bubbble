package com.bubbble.presentation.shotssearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bubbble.R
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.SearchQueryListener
import com.bubbble.shots.shotslist.ShotComparator
import com.bubbble.shots.shotslist.ShotsAdapter
import com.bubbble.ui.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_shots_search.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class ShotsSearchActivity : BaseMvpActivity(), ShotsSearchView {

    override val layoutRes: Int = R.layout.activity_shots_search

    @Inject
    lateinit var presenterFactory: ShotsSearchPresenter.Factory

    val presenter by moxyPresenter {
        val searchQuery = intent.getStringExtra(KEY_SEARCH_QUERY)!!
        presenterFactory.create(searchQuery)
    }

    private val shotsAdapter: ShotsAdapter by lazy {
        ShotsAdapter(ShotComparator) {
            presenter.onShotClick(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()

        lifecycleScope.launch {
            shotsAdapter.loadStateFlow.collectLatest { loadState ->
                presenter.onListStateChanged(loadState)
            }
        }
        val loadingStateAdapter = LoadingStateAdapter {
            presenter.retryLoading()
        }
        shotsList.adapter = shotsAdapter.withLoadStateFooter(loadingStateAdapter)

        noNetworkLayout.findViewById<View>(R.id.retryButton)!!
            .setOnClickListener { presenter.retryLoading() }
    }

    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.shots_search)
        initOptionsMenu(toolbar.menu)
    }

    private fun initOptionsMenu(menu: Menu) {
        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchQueryListener() {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.onNewSearchQuery(query)
                return true
            }
        })
    }

    override fun showPagingData(pagingData: PagingData<Shot>) {
        lifecycleScope.launch {
            shotsAdapter.submitData(pagingData)
        }
    }


    override fun updateListState(
        isProgressBarVisible: Boolean,
        isRetryVisible: Boolean,
        isErrorMsgVisible: Boolean
    ) {
        loadingLayout.isVisible = isProgressBarVisible
        noNetworkLayout.isVisible = isRetryVisible
        //ToDo: errorMsg.isVisible = isErrorMsgVisible
    }

    override fun retryLoading() = shotsAdapter.retry()

    companion object {
        private const val KEY_SEARCH_QUERY = "key_search_query"

        fun buildIntent(context: Context, searchQuery: String?): Intent {
            val intent = Intent(context, ShotsSearchActivity::class.java)
            intent.putExtra(KEY_SEARCH_QUERY, searchQuery)
            return intent
        }
    }
}