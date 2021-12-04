package com.bubbble.shotsearch

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.SearchQueryListener
import com.bubbble.coreui.utils.ShotComparator
import com.bubbble.shotsearch.api.ShotsSearchScreen
import com.bubbble.shotsearch.databinding.ActivityShotsSearchBinding
import com.bubbble.coreui.ui.adapters.LoadingStateAdapter
import com.bubbble.ui.extensions.isVisible
import com.bubbble.coreui.navigationargs.getScreenData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class ShotsSearchActivity : BaseMvpActivity(), ShotsSearchView {

    override val layoutRes: Int = R.layout.activity_shots_search

    private val binding: ActivityShotsSearchBinding by viewBinding()

    @Inject
    lateinit var presenterFactory: ShotsSearchPresenter.Factory

    val presenter by moxyPresenter {
        val data = getScreenData<ShotsSearchScreen.Data>()
        presenterFactory.create(data.query)
    }

    private val shotsAdapter: ShotsSearchAdapter by lazy {
        ShotsSearchAdapter(ShotComparator) {
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
        binding.shotsList.adapter = shotsAdapter.withLoadStateFooter(loadingStateAdapter)

        binding.noNetworkLayout.retryButton
            .setOnClickListener { presenter.retryLoading() }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.shots_search)
        initOptionsMenu()
    }

    private fun initOptionsMenu() {
        val myActionMenuItem = binding.toolbar.menu.findItem(R.id.action_search)
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
        binding.loadingLayout.isVisible = isProgressBarVisible
        binding.noNetworkLayout.isVisible = isRetryVisible
        //ToDo: errorMsg.isVisible = isErrorMsgVisible
    }

    override fun retryLoading() = shotsAdapter.retry()
}