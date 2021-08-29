package com.imangazalievm.bubbble.presentation.userprofile.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.imangazalievm.bubbble.BubbbleApplication.Companion.component
import com.imangazalievm.bubbble.R
import com.imangazalievm.bubbble.di.userprofile.DaggerUserDetailsPresenterComponent
import com.imangazalievm.bubbble.di.userprofile.UserDetailsPresenterModule
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.presentation.global.ui.base.BaseMvpFragment
import com.imangazalievm.bubbble.presentation.global.utils.AppUtils
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class UserDetailsFragment : BaseMvpFragment(), UserDetailsView {

    override val layoutRes: Int = R.layout.fragment_user_details

    @InjectPresenter
    lateinit var shotsPresenter: UserDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): UserDetailsPresenter {
        val userId = requireArguments().getLong(USER_ID_ARG)
        val presenterComponent = DaggerUserDetailsPresenterComponent.builder()
            .applicationComponent(component)
            .userDetailsPresenterModule(UserDetailsPresenterModule(userId))
            .build()
        return presenterComponent.getPresenter()
    }

    private lateinit var loadingLayout: View
    private lateinit var noNetworkLayout: View
    private lateinit var userShotsButton: View
    private lateinit var userLikesButton: View
    private lateinit var userBucketsButton: View
    private lateinit var userFollowersButton: View
    private lateinit var userFollowingButton: View
    private lateinit var userProjectsButton: View
    private lateinit var userShotsCount: TextView
    private lateinit var userLikesCount: TextView
    private lateinit var userBucketsCount: TextView
    private lateinit var userFollowersCount: TextView
    private lateinit var userFollowingCount: TextView
    private lateinit var userProjectsCount: TextView
    private lateinit var userLocation: TextView
    private lateinit var userTwitter: TextView
    private lateinit var userWebsite: TextView
    private lateinit var userTwitterButton: View
    private lateinit var userWebsiteButton: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        loadingLayout = view.findViewById(R.id.loading_layout)
        noNetworkLayout = view.findViewById(R.id.no_network_layout)
        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { shotsPresenter.retryLoading() }
        userShotsButton = view.findViewById(R.id.user_shots_button)
        userLikesButton = view.findViewById(R.id.user_likes_button)
        userBucketsButton = view.findViewById(R.id.user_buckets_button)
        userFollowersButton = view.findViewById(R.id.user_followers_button)
        userFollowingButton = view.findViewById(R.id.user_followings_button)
        userProjectsButton = view.findViewById(R.id.user_projects_button)
        userShotsCount = view.findViewById(R.id.user_shots_count)
        userLikesCount = view.findViewById(R.id.user_likes_count)
        userBucketsCount = view.findViewById(R.id.user_buckets_count)
        userFollowersCount = view.findViewById(R.id.user_followers_count)
        userFollowingCount = view.findViewById(R.id.user_followings_count)
        userProjectsCount = view.findViewById(R.id.user_projects_count)
        userLocation = view.findViewById(R.id.user_location)
        userTwitter = view.findViewById(R.id.user_twitter)
        userWebsite = view.findViewById(R.id.user_website)
        userTwitterButton = view.findViewById(R.id.user_twitter_button)
        userWebsiteButton = view.findViewById(R.id.user_website_button)
        userTwitterButton.setOnClickListener(View.OnClickListener { v: View? -> shotsPresenter.onUserTwitterButtonClicked() })
        userWebsiteButton.setOnClickListener(View.OnClickListener { v: View? -> shotsPresenter.onUserWebsiteButtonClicked() })
    }

    override fun showUserInfo(user: User) {
        userShotsCount.text =
            resources.getQuantityString(R.plurals.shots, user.shotsCount, user.shotsCount)
        userLikesCount.text =
            resources.getQuantityString(R.plurals.likes, user.likesCount, user.likesCount)
        userBucketsCount.text =
            resources.getQuantityString(R.plurals.buckets, user.bucketsCount, user.bucketsCount)
        userFollowersCount.text =
            resources.getQuantityString(
                R.plurals.followers,
                user.followersCount,
                user.followersCount
            )
        userFollowingCount.text =
            resources.getQuantityString(
                R.plurals.following,
                user.followingsCount,
                user.followingsCount
            )
        userProjectsCount.text =
            resources.getQuantityString(R.plurals.projects, user.projectsCount, user.projectsCount)
        userLocation.text = user.location
        val userTwitterUrl = user.links.twitter
        if (userTwitterUrl != null) {
            userTwitterButton.visibility = View.VISIBLE
            userTwitter.text = getTwitterUserName(userTwitterUrl)
        }
        val userWebsiteUrl = user.links.web
        if (userWebsiteUrl != null) {
            userWebsiteButton.visibility = View.VISIBLE
            userWebsite.text = userWebsiteUrl
        }
    }

    private fun getTwitterUserName(twitterUrl: String): String {
        return twitterUrl.toHttpUrl().pathSegments[0]
    }

    override fun showLoadingProgress() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        loadingLayout.visibility = View.GONE
    }

    override fun showNoNetworkLayout() {
        noNetworkLayout.visibility = View.VISIBLE
    }

    override fun hideNoNetworkLayout() {
        noNetworkLayout.visibility = View.GONE
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(activity, url)
    }

    companion object {
        private const val USER_ID_ARG = "user_id"
        fun newInstance(userId: Long): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            val args = Bundle()
            args.putLong(USER_ID_ARG, userId)
            fragment.arguments = args
            return fragment
        }
    }

}