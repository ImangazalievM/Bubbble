package com.bubbble.presentation.userprofile.shots

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.bubbble.R
import com.bubbble.core.models.shot.Shot
import com.bubbble.shots.shotslist.ShotsAdapter
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.coreui.ui.commons.EndlessRecyclerOnScrollListener
import com.bubbble.shotdetails.ShotDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserShotsFragment : BaseMvpFragment(), UserShotsView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: UserShotsPresenter.Factory

    val presenter by moxyPresenter {
        val userName = requireArguments().getString(USER_NAME)!!
        presenterFactory.create(userName)
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

        noNetworkLayout.findViewById<View>(R.id.retry_button)
        shotsRecyclerView.layoutManager = shotsListLayoutManager
        shotsRecyclerView.adapter = shotsAdapter
        shotsAdapter.setOnItemClickListener { position: Int ->
            presenter.onShotClick(position)
        }
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
        private const val USER_NAME = "user_name"

        @JvmStatic
        fun newInstance(userName: String): UserShotsFragment {
            val fragment = UserShotsFragment()
            val args = Bundle()
            args.putString(USER_NAME, userName)
            fragment.arguments = args
            return fragment
        }
    }
}