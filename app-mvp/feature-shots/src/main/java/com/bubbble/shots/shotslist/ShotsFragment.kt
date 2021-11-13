package com.bubbble.shots.shotslist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.shots.R
import com.bubbble.ui.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shots.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class ShotsFragment : BaseMvpFragment(), ShotsView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: ShotsPresenter.Factory

    val presenter by moxyPresenter {
        val sortType = requireArguments().getString(SORT_TYPE_ARG)!!
        presenterFactory.create(ShotsFeedParams.Sorting.find(sortType)!!)
    }

    private val shotsAdapter: ShotsAdapter by lazy {
        ShotsAdapter(ShotComparator) {
            presenter.onShotClick(it)
        }
    }
    private val shotsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            shotsAdapter.loadStateFlow.collectLatest { loadState ->
                presenter.onListStateChanged(loadState)
            }
        }
        val loadingStateAdapter = LoadingStateAdapter {
            presenter.retryLoading()
        }
        shotsList.adapter = shotsAdapter.withLoadStateFooter(loadingStateAdapter)
        noNetworkLayout.findViewById<View>(R.id.retryButton)
            .setOnClickListener { presenter.retryLoading() }
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
        private const val SORT_TYPE_ARG = "sort"

        @JvmStatic
        fun newInstance(sort: ShotsFeedParams.Sorting): ShotsFragment {
            val fragment = ShotsFragment()
            val args = Bundle()
            args.putString(SORT_TYPE_ARG, sort.name)
            fragment.arguments = args
            return fragment
        }
    }
}