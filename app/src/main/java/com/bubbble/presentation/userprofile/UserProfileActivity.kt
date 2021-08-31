package com.bubbble.presentation.userprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.bubbble.R
import com.bubbble.domain.global.models.User
import com.bubbble.presentation.global.ui.base.BaseMvpActivity
import com.bubbble.presentation.global.ui.commons.glide.GlideBlurTransformation
import com.bubbble.presentation.global.ui.commons.glide.GlideCircleTransform
import com.bubbble.presentation.global.ui.views.dribbbletextview.DribbbleTextView
import com.bubbble.presentation.global.utils.AppUtils
import com.bubbble.presentation.userprofile.details.UserDetailsFragment
import com.bubbble.presentation.userprofile.followers.UserFollowersFragment
import com.bubbble.presentation.userprofile.shots.UserShotsFragment
import javax.inject.Inject
import kotlin.math.abs

class UserProfileActivity : BaseMvpActivity(), UserProfileView {

    override val layoutRes: Int = R.layout.activity_user_profile

    private val loadingLayout: View by lazy {
        findViewById(R.id.loading_layout)
    }
    private val noNetworkLayout: View by lazy {
        findViewById(R.id.no_network_layout)
    }
    private val appBarLayout: AppBarLayout by lazy {
        findViewById(R.id.app_bar_layout)
    }
    private val toolbarTitle: TextView by lazy {
        findViewById(R.id.toolbar_title)
    }
    private val userAvatarContainer: RelativeLayout by lazy {
        findViewById(R.id.header_image_container)
    }
    private val headerBackground: ImageView by lazy {
        findViewById(R.id.header_background)
    }
    private val userAvatar: ImageView by lazy {
        findViewById(R.id.user_avatar)
    }
    private val userName: TextView by lazy {
        findViewById(R.id.user_name)
    }
    private val userBio: DribbbleTextView by lazy {
        findViewById(R.id.user_bio)
    }
    private val followButton: TextView by lazy {
        findViewById(R.id.user_follow_button)
    }
    private val userProfileViewPager: ViewPager by lazy {
        findViewById(R.id.user_profile_pager)
    }
    private val userProfilePagerTabs: TabLayout by lazy {
        findViewById(R.id.user_profile_pager_tabs)
    }
    private val userProfileContainer: CoordinatorLayout by lazy {
        findViewById(R.id.user_profile_container)
    }

    private var isTheTitleVisible = false

    @Inject
    lateinit var presenterFactory: UserProfilePresenter.Factory

    @InjectPresenter
    lateinit var presenter: UserProfilePresenter

    @ProvidePresenter
    fun providePresenter(): UserProfilePresenter {
        val userId = intent.getLongExtra(USER_ID, 0L)
        return presenterFactory.create(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.user_profile)
        toolbar.setOnMenuItemClickListener { item: MenuItem -> onMenuItemClick(item) }
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
        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { presenter.retryLoading() }
        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
            handleToolbarTitleVisibility(percentage)
        })
        userBio.setOnLinkClickListener { url: String -> presenter.onLinkClicked(url) }
        userBio.setOnUserSelectedListener { useId: Long ->
            presenter.onUserSelected(
                useId
            )
        }
        followButton.setOnClickListener { v: View? ->
            Toast.makeText(
                this,
                "Coming soon",
                Toast.LENGTH_SHORT
            ).show()
        }
        userProfilePagerTabs.setupWithViewPager(userProfileViewPager)
        initParallaxValues()
    }

    private fun initParallaxValues() {
        val headerBackgroundLp =
            headerBackground.layoutParams as CollapsingToolbarLayout.LayoutParams
        headerBackgroundLp.parallaxMultiplier = PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR
        headerBackground.layoutParams = headerBackgroundLp
        val userAvatarContainerContainerLp =
            userAvatarContainer.layoutParams as CollapsingToolbarLayout.LayoutParams
        userAvatarContainerContainerLp.parallaxMultiplier = PERCENTAGE_TO_HIDE_TITLE_DETAILS
        userAvatarContainer.layoutParams = userAvatarContainerContainerLp
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!isTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                isTheTitleVisible = true
            }
        } else {
            if (isTheTitleVisible) {
                startAlphaAnimation(
                    toolbarTitle,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isTheTitleVisible = false
            }
        }
    }

    override fun showUser(user: User) {
        userProfileContainer.visibility = View.VISIBLE
        toolbarTitle.text = user.name
        userName.text = user.name
        if (user.bio.isNullOrEmpty()) {
            userBio.visibility = View.VISIBLE
            userBio.setHtmlText(user.bio)
        }
        Glide.with(this)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(GlideCircleTransform(this))
            .into(userAvatar)
        Glide.with(this)
            .load(user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .bitmapTransform(GlideBlurTransformation(this, 25))
            .into(headerBackground)
        setupProfilePager(user)
    }

    private fun setupProfilePager(user: User) {
        val userProfilePagerAdapter = UserProfilePagerAdapter(supportFragmentManager)
        userProfilePagerAdapter.addFragment(
            UserDetailsFragment.newInstance(user.id),
            resources.getString(R.string.user_information)
        )
        userProfilePagerAdapter.addFragment(
            UserShotsFragment.newInstance(user.id),
            resources.getQuantityString(R.plurals.shots, user.shotsCount, user.shotsCount)
        )
        userProfilePagerAdapter.addFragment(
            UserFollowersFragment.newInstance(user.id),
            resources.getQuantityString(
                R.plurals.followers,
                user.followersCount,
                user.followersCount
            )
        )
        userProfileViewPager.adapter = userProfilePagerAdapter
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

    override fun openUserProfileScreen(userId: Long) {
        startActivity(buildIntent(this, userId))
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
        private const val USER_ID = "user_id"
        fun buildIntent(context: Context?, userId: Long): Intent {
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_ID, userId)
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