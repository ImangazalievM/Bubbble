package com.bubbble.presentation.userprofile.followers

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.R
import com.bubbble.core.models.user.Follow
import com.bubbble.presentation.userprofile.UserFollowersAdapter
import com.bubbble.coreui.ui.base.BaseMvpFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserFollowersFragment : BaseMvpFragment(), UserFollowersView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: UserFollowersPresenter.Factory

    val presenter by moxyPresenter {
        val userName = requireArguments().getString(USER_NAME)!!
        presenterFactory.create(userName)
    }

    private val loadingLayout: View by lazy {
        requireView().findViewById(R.id.loadingLayout)
    }
    private val noNetworkLayout: View by lazy {
        requireView().findViewById(R.id.noNetworkLayout)
    }
    private val followersRecyclerView: RecyclerView by lazy {
        requireView().findViewById(R.id.shotsList)
    }
    private val userFollowersAdapter: UserFollowersAdapter by lazy {
        UserFollowersAdapter(context)
    }
    private val followersListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {

        noNetworkLayout.findViewById<View>(R.id.retryButton)
            .setOnClickListener { presenter.retryLoading() }
        followersRecyclerView.layoutManager = followersListLayoutManager
        followersRecyclerView.adapter = userFollowersAdapter
        userFollowersAdapter.setOnItemClickListener { position: Int ->
            presenter.onFollowerClick(
                position
            )
        }
       // followersRecyclerView.addOnScrollListener(object :
       //     EndlessRecyclerOnScrollListener(followersListLayoutManager) {
       //     override fun onLoadMore() {
       //         presenter.onLoadMoreFollowersRequest()
       //     }
       // })
    }

    override fun showNewFollowers(newFollowers: List<Follow>) {
        followersRecyclerView.visibility = View.VISIBLE
        userFollowersAdapter.addItems(newFollowers)
    }

    override fun showFollowersLoadingProgress() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun hideFollowersLoadingProgress() {
        loadingLayout.visibility = View.GONE
    }

    override fun showFollowersLoadingMoreProgress() {
        userFollowersAdapter.showLoadingIndicator()
    }

    override fun hideFollowersLoadingMoreProgress() {
        userFollowersAdapter.hideLoadingIndicator()
    }

    override fun showNoNetworkLayout() {
        noNetworkLayout.visibility = View.VISIBLE
    }

    override fun hideNoNetworkLayout() {
        noNetworkLayout.visibility = View.GONE
    }

    override fun showLoadMoreError() {
        userFollowersAdapter.setLoadingError(true)
    }

    companion object {
        private const val USER_NAME = "user_name"

        @JvmStatic
        fun newInstance(userName: String): UserFollowersFragment {
            val fragment = UserFollowersFragment()
            val args = Bundle()
            args.putString(USER_NAME, userName)
            fragment.arguments = args
            return fragment
        }
    }

}