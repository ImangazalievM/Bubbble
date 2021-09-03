package com.bubbble.presentation.userprofile.shots

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bubbble.R
import com.bubbble.models.Shot
import com.bubbble.presentation.global.ui.adapters.ShotsAdapter
import com.bubbble.presentation.global.ui.base.BaseMvpFragment
import com.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener
import com.bubbble.presentation.shotdetails.ShotDetailsActivity
import javax.inject.Inject

class UserShotsFragment : BaseMvpFragment(), UserShotsView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: UserShotsPresenter.Factory

    @InjectPresenter
    lateinit var presenter: UserShotsPresenter
    
    @ProvidePresenter
    fun providePresenter(): UserShotsPresenter {
        val userId = requireArguments().getLong(USER_ID_ARG)
        return presenterFactory.create(userId)
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
        shotsRecyclerView.layoutManager = shotsListLayoutManager
        shotsRecyclerView.adapter = shotsAdapter
        shotsAdapter.setOnItemClickListener { position: Int ->
            presenter.onShotClick(
                position
            )
        }
        shotsRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
            override fun onLoadMore() {
                presenter.onLoadMoreShotsRequest()
            }
        })
    }

    override fun showNewShots(newShots: List<com.bubbble.models.Shot>) {
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
        private const val USER_ID_ARG = "user_id"
        @JvmStatic
        fun newInstance(userId: Long): UserShotsFragment {
            val fragment = UserShotsFragment()
            val args = Bundle()
            args.putLong(USER_ID_ARG, userId)
            fragment.arguments = args
            return fragment
        }
    }
}