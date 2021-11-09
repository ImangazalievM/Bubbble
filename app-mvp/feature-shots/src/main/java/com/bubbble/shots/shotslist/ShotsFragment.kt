package com.bubbble.shots.shotslist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.coreui.ui.commons.EndlessRecyclerOnScrollListener
import com.bubbble.shots.R
import dagger.hilt.android.AndroidEntryPoint
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

    private val loadingLayout: View by lazy {
        requireView().findViewById(R.id.loading_layout)
    }
    private val noNetworkLayout: View by lazy {
        requireView().findViewById(R.id.no_network_layout)
    }
    private val shotsRecyclerView: RecyclerView by lazy {
        requireView().findViewById(R.id.shotsRecyclerView)
    }
    private val shotsAdapter: ShotsAdapter by lazy {
        ShotsAdapter(requireContext())
    }
    private val shotsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { presenter.retryLoading() }
        shotsRecyclerView.layoutManager = shotsListLayoutManager
        shotsAdapter.setOnItemClickListener { position: Int ->
            presenter.onShotClick(
                position
            )
        }
        shotsAdapter.setOnRetryLoadMoreListener { presenter.retryLoading() }
        shotsRecyclerView.adapter = shotsAdapter
        shotsRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            override fun onLoadMore() {
                presenter.onLoadMoreShotsRequest()
            }
        })
    }

    override fun showNewShots(newShots: List<Shot>) {
        shotsRecyclerView.visibility = View.VISIBLE
        shotsAdapter.addItems(newShots)
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
        //startActivity(ShotDetailsActivity.buildIntent(activity, shotId))
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