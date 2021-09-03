package com.bubbble.presentation.userprofile.followers

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bubbble.R
import com.bubbble.core.models.Follow
import com.bubbble.presentation.global.ui.adapters.UserFollowersAdapter
import com.bubbble.presentation.global.ui.base.BaseMvpFragment
import com.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener
import com.bubbble.presentation.userprofile.UserProfileActivity
import javax.inject.Inject

class UserFollowersFragment : BaseMvpFragment(), UserFollowersView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: UserFollowersPresenter.Factory

    @InjectPresenter
    lateinit var presenter: UserFollowersPresenter

    @ProvidePresenter
    fun providePresenter(): UserFollowersPresenter {
        val userId = requireArguments().getLong(USER_ID_ARG)
        return presenterFactory.create(userId)
    }

    private val loadingLayout: View by lazy {
        requireView().findViewById(R.id.loading_layout)
    }
    private val noNetworkLayout: View by lazy {
        requireView().findViewById(R.id.no_network_layout)
    }
    private val followersRecyclerView: RecyclerView by lazy {
        requireView().findViewById(R.id.shotsRecyclerView)
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

        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { presenter.retryLoading() }
        followersRecyclerView.layoutManager = followersListLayoutManager
        followersRecyclerView.adapter = userFollowersAdapter
        userFollowersAdapter.setOnItemClickListener { position: Int ->
            presenter.onFollowerClick(
                position
            )
        }
        followersRecyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(followersListLayoutManager) {
            override fun onLoadMore() {
                presenter.onLoadMoreFollowersRequest()
            }
        })
    }

    override fun showNewFollowers(newFollowers: List<com.bubbble.core.models.Follow>) {
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

    override fun openUserDetailsScreen(userId: Long) {
        startActivity(UserProfileActivity.buildIntent(activity, userId))
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
        private const val USER_ID_ARG = "user_id"

        @JvmStatic
        fun newInstance(userId: Long): UserFollowersFragment {
            val fragment = UserFollowersFragment()
            val args = Bundle()
            args.putLong(USER_ID_ARG, userId)
            fragment.arguments = args
            return fragment
        }
    }

}