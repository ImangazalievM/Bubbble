package com.bubbble.presentation.userprofile.details

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.R
import com.bubbble.core.models.user.User
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.coreui.utils.AppUtils
import com.bubbble.ui.extensions.isVisible
import com.bubbble.ui.navigationargs.createFragment
import com.bubbble.ui.navigationargs.getScreenData
import com.bubbble.userprofile.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment : BaseMvpFragment(), UserDetailsView {

    override val layoutRes: Int = R.layout.fragment_user_details

    @Inject
    lateinit var presenterFactory: UserDetailsPresenter.Factory

    val presenter by moxyPresenter {
        val userName = getScreenData<UserDetailsScreenData>().userName
        presenterFactory.create(userName)
    }

    private val binding: FragmentUserDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        binding.noNetworkLayout.retryButton.setOnClickListener { presenter.retryLoading() }
        binding.userTwitterButton.setOnClickListener { presenter.onUserTwitterButtonClicked() }
        binding.userWebsiteButton.setOnClickListener { presenter.onUserWebsiteButtonClicked() }
    }

    override fun showUserInfo(user: User) {
        binding.userShotsCount.text =
            resources.getQuantityString(R.plurals.shots, user.shotsCount, user.shotsCount)
        binding.userLikesCount.text =
            resources.getQuantityString(R.plurals.likes, user.likesCount, user.likesCount)
        binding.userBucketsCount.text =
            resources.getQuantityString(R.plurals.buckets, user.bucketsCount, user.bucketsCount)
        binding.userFollowersCount.text =
            resources.getQuantityString(
                R.plurals.followers,
                user.followersCount,
                user.followersCount
            )
        binding.userFollowersCount.text =
            resources.getQuantityString(
                R.plurals.following,
                user.followingsCount,
                user.followingsCount
            )
        binding.userProjectsCount.text =
            resources.getQuantityString(R.plurals.projects, user.projectsCount, user.projectsCount)
        binding.userLocation.text = user.location
        val userTwitterUrl = user.links.twitter
        binding.userTwitterButton.visibility = View.VISIBLE
        binding.userTwitter.text = getTwitterUserName(userTwitterUrl)
        val userWebsiteUrl = user.links.web
        binding.userWebsiteButton.visibility = View.VISIBLE
        binding.userWebsite.text = userWebsiteUrl
    }

    private fun getTwitterUserName(twitterUrl: String): String {
        return twitterUrl.toHttpUrl().pathSegments[0]
    }

    override fun showLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    override fun showNoNetworkLayout(isVisible: Boolean) {
        binding.noNetworkLayout.isVisible = isVisible
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(activity, url)
    }

    companion object {
        fun newInstance(
            userName: String
        ) = createFragment<UserDetailsFragment>(UserDetailsScreenData(userName))
    }
}