package com.bubbble.presentation.shotssearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bubbble.R
import com.bubbble.domain.global.models.Shot
import com.bubbble.presentation.global.ui.adapters.ShotsAdapter
import com.bubbble.presentation.global.ui.base.BaseMvpActivity
import com.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener
import com.bubbble.presentation.global.ui.commons.SearchQueryListener
import com.bubbble.presentation.shotdetails.ShotDetailsActivity
import javax.inject.Inject

class ShotsSearchActivity : BaseMvpActivity(), ShotsSearchView {

    override val layoutRes: Int = R.layout.activity_shots_search

    @Inject
    lateinit var presenterFactory: ShotsSearchPresenter.Factory

    @InjectPresenter
    lateinit var presenter: ShotsSearchPresenter

    @ProvidePresenter
    fun providePresenter(): ShotsSearchPresenter {
        val searchQuery = intent.getStringExtra(KEY_SEARCH_QUERY)!!
        return presenterFactory.create(searchQuery)
    }

    private val loadingLayout: View by lazy {
        findViewById(R.id.loading_layout)
    }
    private val noNetworkLayout: View by lazy {
        findViewById(R.id.no_network_layout)
    }
    private val shotsRecyclerView: RecyclerView by lazy {
        findViewById<View>(R.id.shotsRecyclerView) as RecyclerView
    }
    private val shotsAdapter: ShotsAdapter by lazy {
        ShotsAdapter(this)
    }
    private val shotsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.shots_search)
        initOptionsMenu(toolbar.menu)
    }

    fun initOptionsMenu(menu: Menu) {
        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchQueryListener() {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.onNewSearchQuery(query)
                return true
            }
        })
    }

    private fun initViews() {
        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { presenter.retryLoading() }
        shotsRecyclerView.layoutManager = shotsListLayoutManager
        shotsRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            override fun onLoadMore() {
                presenter.onLoadMoreShotsRequest()
            }
        })
        initRecyclerViewAdapter()
    }

    private fun initRecyclerViewAdapter() {
        shotsAdapter.setOnItemClickListener { position: Int ->
            presenter.onShotClick(
                position
            )
        }
        shotsAdapter.setOnRetryLoadMoreListener { presenter.retryLoading() }
        shotsRecyclerView.adapter = shotsAdapter
    }

    override fun showNewShots(newShots: List<Shot>) {
        shotsRecyclerView.visibility = View.VISIBLE
        shotsAdapter.addItems(newShots)
    }

    override fun clearShotsList() {
        initRecyclerViewAdapter()
        shotsRecyclerView.visibility = View.GONE
    }

    override fun showShotsLoadingProgress() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun hideShotsLoadingProgress() {
        loadingLayout.visibility = View.GONE
    }

    override fun showShotsLoadingMoreProgress() {
        shotsAdapter.setLoadingMore(true)
    }

    override fun hideShotsLoadingMoreProgress() {
        shotsAdapter.setLoadingMore(false)
    }

    override fun openShotDetailsScreen(shotId: Long) {
        startActivity(ShotDetailsActivity.buildIntent(this, shotId))
    }

    override fun showNoNetworkLayout() {
        noNetworkLayout.visibility = View.VISIBLE
    }

    override fun hideNoNetworkLayout() {
        noNetworkLayout.visibility = View.GONE
    }

    override fun showLoadMoreError() {
        shotsAdapter.setLoadingError(true)
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "key_search_query"

        @JvmStatic
        fun buildIntent(context: Context?, searchQuery: String?): Intent {
            val intent = Intent(context, ShotsSearchActivity::class.java)
            intent.putExtra(KEY_SEARCH_QUERY, searchQuery)
            return intent
        }
    }
}