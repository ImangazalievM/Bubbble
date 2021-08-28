package com.imangazalievm.bubbble.presentation.shotslist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.imangazalievm.bubbble.BubbbleApplication
import com.imangazalievm.bubbble.R
import com.imangazalievm.bubbble.di.shotslist.DaggerShotsPresenterComponent
import com.imangazalievm.bubbble.di.shotslist.ShotsPresenterModule
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.presentation.global.ui.adapters.ShotsAdapter
import com.imangazalievm.bubbble.presentation.global.ui.base.BaseMvpFragment
import com.imangazalievm.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsActivity

class ShotsFragment : BaseMvpFragment(), ShotsView {

    override val layoutRes: Int = R.layout.fragment_shots

    @InjectPresenter
    lateinit var shotsPresenter: ShotsPresenter
    
    @ProvidePresenter
    fun providePresenter(): ShotsPresenter {
        val sortType = requireArguments().getString(SORT_TYPE_ARG)
        val shotsPresenterComponent = DaggerShotsPresenterComponent.builder()
            .applicationComponent(BubbbleApplication.component)
            .shotsPresenterModule(ShotsPresenterModule(sortType!!))
            .build()
        return shotsPresenterComponent.getPresenter()
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
        ShotsAdapter(context)
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
            .setOnClickListener { shotsPresenter.retryLoading() }
        shotsRecyclerView.layoutManager = shotsListLayoutManager
        shotsAdapter.setOnItemClickListener { position: Int ->
            shotsPresenter.onShotClick(
                position
            )
        }
        shotsAdapter.setOnRetryLoadMoreListener { shotsPresenter.retryLoading() }
        shotsRecyclerView.setAdapter(shotsAdapter)
        shotsRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            override fun onLoadMore() {
                shotsPresenter.onLoadMoreShotsRequest()
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
        startActivity(ShotDetailsActivity.buildIntent(activity, shotId))
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
        fun newInstance(sort: String?): ShotsFragment {
            val fragment = ShotsFragment()
            val args = Bundle()
            args.putString(SORT_TYPE_ARG, sort)
            fragment.arguments = args
            return fragment
        }
    }
}