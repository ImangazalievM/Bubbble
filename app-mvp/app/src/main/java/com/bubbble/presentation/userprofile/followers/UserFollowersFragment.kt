package com.bubbble.presentation.userprofile.followers

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.R
import com.bubbble.core.models.user.Follow
import com.bubbble.presentation.userprofile.UserFollowersAdapter
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.shots.databinding.FragmentFollowersBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserFollowersFragment : BaseMvpFragment(), UserFollowersView {

    override val layoutRes: Int = R.layout.fragment_followers

    @Inject
    lateinit var presenterFactory: UserFollowersPresenter.Factory

    val presenter by moxyPresenter {
        val userName = requireArguments().getString(USER_NAME)!!
        presenterFactory.create(userName)
    }

    private val binding: FragmentFollowersBinding by viewBinding()
    private val userFollowersAdapter: UserFollowersAdapter by lazy {
        UserFollowersAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noNetworkLayout.retryButton
            .setOnClickListener { presenter.retryLoading() }
        binding.followersList.adapter = userFollowersAdapter
        userFollowersAdapter.onItemClickListener = {
            presenter.onFollowerClick(it)
        }
    }

    override fun showNewFollowers(newFollowers: List<Follow>) {
        binding.followersList.visibility = View.VISIBLE
        userFollowersAdapter.addItems(newFollowers)
    }

    override fun showFollowersLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.root.visibility = View.VISIBLE
    }

    override fun showFollowersLoadingMoreProgress(isVisible: Boolean) {
        userFollowersAdapter.showLoadingIndicator()
    }

    override fun hideFollowersLoadingMoreProgress() {
        userFollowersAdapter.hideLoadingIndicator()
    }

    override fun hideNoNetworkLayout() {
        binding.noNetworkLayout.visibility = View.GONE
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