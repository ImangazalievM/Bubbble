package com.bubbble.userprofile.followers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.core.models.user.Follow
import com.bubbble.userprofile.UserFollowersAdapter
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.ui.extensions.isVisible
import com.bubbble.coreui.navigationargs.createFragment
import com.bubbble.coreui.navigationargs.getScreenData
import com.bubbble.userprofile.R
import com.bubbble.userprofile.databinding.FragmentFollowersBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserFollowersFragment : BaseMvpFragment(), UserFollowersView {

    override val layoutRes: Int = R.layout.fragment_followers

    @Inject
    lateinit var presenterFactory: UserFollowersPresenter.Factory

    val presenter by moxyPresenter {
        val userName = getScreenData<UserFollowersScreenData>().userName
        presenterFactory.create(userName)
    }

    private val binding: FragmentFollowersBinding by viewBinding()
    private val userFollowersAdapter: UserFollowersAdapter by lazy {
        UserFollowersAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noNetworkLayout.retryButton.setOnClickListener {
            presenter.retryLoading()
        }
        binding.followersList.adapter = userFollowersAdapter
        userFollowersAdapter.onItemClickListener = {
            presenter.onFollowerClick(it)
        }
    }

    override fun showNewFollowers(newFollowers: List<Follow>) {
        binding.followersList.isVisible = true
        userFollowersAdapter.addItems(newFollowers)
    }

    override fun showFollowersLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    override fun showFollowersLoadingMoreProgress(isVisible: Boolean) {
        userFollowersAdapter.showLoadingIndicator(isVisible)
    }

    override fun showNoNetworkLayout(isVisible: Boolean) {
        binding.noNetworkLayout.isVisible = isVisible
    }

    override fun showLoadMoreError() {
        userFollowersAdapter.setLoadingError(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            userName: String
        ) = createFragment<UserFollowersFragment>(UserFollowersScreenData(userName))
    }

}