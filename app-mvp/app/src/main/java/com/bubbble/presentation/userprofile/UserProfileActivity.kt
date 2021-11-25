package com.bubbble.presentation.userprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.bubbble.R
import com.bubbble.core.models.user.User
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.glide.GlideBlurTransformation
import com.bubbble.coreui.ui.commons.glide.GlideCircleTransform
import com.bubbble.coreui.utils.AppUtils
import com.bubbble.presentation.userprofile.details.UserDetailsFragment
import com.bubbble.presentation.userprofile.followers.UserFollowersFragment
import com.bubbble.presentation.userprofile.shots.UserShotsFragment
import com.bubbble.ui.extensions.isVisible
import com.bubbble.userprofile.databinding.ActivityUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class UserProfileActivity : BaseMvpActivity(), UserProfileView {

    override val layoutRes: Int = R.layout.activity_user_profile

    private val binding: ActivityUserProfileBinding by viewBinding()
    private var isTheTitleVisible = false

    @Inject
    lateinit var presenterFactory: UserProfilePresenter.Factory

    val presenter by moxyPresenter {
        val userName = intent.getStringExtra(USER_NAME)!!
        presenterFactory.create(userName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.user_profile)
        binding.toolbar.setOnMenuItemClickListener { item: MenuItem -> onMenuItemClick(item) }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                presenter.onBackPressed()
                return true
            }
            R.id.share_profile -> {
                presenter.onShareProfileClicked()
                return true
            }
            R.id.open_in_browser -> {
                presenter.onOpenInBrowserClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        binding.noNetworkLayout.retryButton
            .setOnClickListener { presenter.retryLoading() }
        binding.appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
            handleToolbarTitleVisibility(percentage)
        })
        binding.userBio.setOnLinkClickListener { url: String -> presenter.onLinkClicked(url) }
        binding.userBio.setOnUserSelectedListener { userUrl: String ->
            presenter.onUserSelected(userUrl)
        }
        binding.followButton.setOnClickListener {
            Toast.makeText(
                this,
                "Coming soon",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.userProfilePagerTabs.setupWithViewPager(binding.userProfilePager)
        initParallaxValues()
    }

    private fun initParallaxValues() {
        val headerBackgroundLp =
            binding.headerBackground.layoutParams as CollapsingToolbarLayout.LayoutParams
        headerBackgroundLp.parallaxMultiplier = PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR
        binding.headerBackground.layoutParams = headerBackgroundLp
        val userAvatarContainerContainerLp =
            binding.headerImageContainer.layoutParams as CollapsingToolbarLayout.LayoutParams
        userAvatarContainerContainerLp.parallaxMultiplier = PERCENTAGE_TO_HIDE_TITLE_DETAILS
        binding.headerImageContainer.layoutParams = userAvatarContainerContainerLp
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!isTheTitleVisible) {
                startAlphaAnimation(
                    binding.toolbarTitle,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.VISIBLE
                )
                isTheTitleVisible = true
            }
        } else {
            if (isTheTitleVisible) {
                startAlphaAnimation(
                    binding.toolbarTitle,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isTheTitleVisible = false
            }
        }
    }

    override fun showUser(user: User) {
        binding.userProfileContainer.visibility = View.VISIBLE
        binding.toolbarTitle.text = user.name
        binding.userName.text = user.name
        if (user.bio.isNullOrEmpty()) {
            binding.userBio.visibility = View.VISIBLE
            binding.userBio.setHtmlText(user.bio)
        }
        Glide.with(this)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(GlideCircleTransform(this))
            .into(binding.userAvatar)
        Glide.with(this)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .bitmapTransform(GlideBlurTransformation(this, 25))
            .into(binding.headerBackground)
        setupProfilePager(user)
    }

    private fun setupProfilePager(user: User) {
        val userProfilePagerAdapter = UserProfilePagerAdapter(supportFragmentManager)
        userProfilePagerAdapter.addFragment(
            UserDetailsFragment.newInstance(user.userName),
            resources.getString(R.string.user_information)
        )
        userProfilePagerAdapter.addFragment(
            UserShotsFragment.newInstance(user.userName),
            resources.getQuantityString(R.plurals.shots, user.shotsCount, user.shotsCount)
        )
        userProfilePagerAdapter.addFragment(
            UserFollowersFragment.newInstance(user.userName),
            resources.getQuantityString(
                R.plurals.followers,
                user.followersCount,
                user.followersCount
            )
        )
        binding.userProfilePager.adapter = userProfilePagerAdapter
    }

    override fun showLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    override fun showNoNetworkLayout(isVisible: Boolean) {
        binding.noNetworkLayout.isVisible = isVisible
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(this, url)
    }

    override fun showUserProfileSharing(user: User) {
        AppUtils.sharePlainText(this, String.format("%s - %s", user.name, user.htmlUrl))
    }

    override fun closeScreen() {
        finish()
    }

    companion object {
        private const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
        private const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = -0.3f
        private const val ALPHA_ANIMATIONS_DURATION = 200
        private const val USER_NAME = "user_name"

        fun buildIntent(context: Context, userName: String): Intent {
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_NAME, userName)
            return intent
        }

        fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
            val alphaAnimation =
                if (visibility == View.VISIBLE) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
            alphaAnimation.duration = duration
            alphaAnimation.fillAfter = true
            v.startAnimation(alphaAnimation)
        }
    }
}