package com.imangazalievm.bubbble.presentation.shotdetails

import android.content.Context
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.imangazalievm.bubbble.presentation.global.ui.adapters.ShotCommentsAdapter
import com.imangazalievm.bubbble.presentation.global.ui.views.dribbbletextview.DribbbleTextView
import com.greenfrvr.hashtagview.HashtagView
import androidx.recyclerview.widget.LinearLayoutManager
import com.imangazalievm.bubbble.presentation.global.ui.commons.EndlessRecyclerOnScrollListener
import com.arellomobile.mvp.presenter.InjectPresenter
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsActivity
import com.imangazalievm.bubbble.di.shotdetails.ShotDetailsPresenterModule
import com.imangazalievm.bubbble.di.shotdetails.ShotDetailsPresenterComponent
import com.imangazalievm.bubbble.di.shotdetails.DaggerShotDetailsPresenterComponent
import com.imangazalievm.bubbble.BubbbleApplication
import android.os.Bundle
import com.imangazalievm.bubbble.R
import com.imangazalievm.bubbble.presentation.global.ui.commons.AndroidPermissionsManager
import com.greenfrvr.hashtagview.HashtagView.TagsClickListener
import android.widget.Toast
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.imangazalievm.bubbble.presentation.global.ui.commons.glide.GlideCircleTransform
import com.google.android.material.snackbar.Snackbar
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.imangazalievm.bubbble.domain.global.models.Comment
import com.imangazalievm.bubbble.presentation.global.ui.base.MvpAppCompatActivity
import com.imangazalievm.bubbble.presentation.global.utils.AppUtils
import com.imangazalievm.bubbble.presentation.userprofile.UserProfileActivity
import com.imangazalievm.bubbble.presentation.shotzoom.ShotZoomActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ShotDetailsActivity : MvpAppCompatActivity(), ShotDetailsView {


    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }
    private val shotImage: ImageView by lazy {
        findViewById(R.id.shot_image)
    }
    private val shotImageProgressBar: ProgressBar by lazy {
        findViewById(R.id.shot_image_progress_bar)
    }
    private val shotDescription: View by lazy {
        layoutInflater.inflate(
            R.layout.item_shot_description,
            commentsList,
            false
        )
    }
    private val shotDetailContainer by lazy {
        findViewById<View>(R.id.shot_detail_container) as CoordinatorLayout
    }
    private val loadingLayout: View by lazy {
        findViewById(R.id.loading_layout)
    }
    private val noNetworkLayout: View by lazy {
        findViewById(R.id.no_network_layout)
    }
    private val commentsList: RecyclerView by lazy {
        findViewById(R.id.shot_comments)
    }
    private val title: TextView by lazy {
        shotDescription.findViewById(R.id.shot_title)
    }
    private val description: DribbbleTextView by lazy {
        shotDescription.findViewById(R.id.shot_description)
    }

    private val userProfileLayout: View by lazy {
        shotDescription.findViewById(R.id.user_profile_layout)
    }
    private val userName: TextView by lazy {
        shotDescription.findViewById(R.id.user_name)
    }
    private val userAvatar: ImageView by lazy {
        shotDescription.findViewById(R.id.user_avatar)
    }
    private val shotCreateDate: TextView by lazy {
        shotDescription.findViewById(R.id.shot_create_date)
    }
    private val likesCount: TextView by lazy {
        shotDescription.findViewById(R.id.shot_likes_count)
    }
    private val viewsCount: TextView by lazy {
        shotDescription.findViewById(R.id.shot_views_count)
    }
    private val bucketsCount: TextView by lazy {
        shotDescription.findViewById(R.id.shot_buckets_count)
    }
    private val shareShotButton: TextView by lazy {
        shotDescription.findViewById(R.id.share_shot)
    }
    private val hashtagView: HashtagView by lazy {
        shotDescription.findViewById(R.id.shot_tags)
    }

    private val commentsAdapter: ShotCommentsAdapter by lazy {
        ShotCommentsAdapter(this, shotDescription)
    }
    private val commentsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val endlessRecyclerOnScrollListener =
        object : EndlessRecyclerOnScrollListener(commentsListLayoutManager) {
            override fun onLoadMore() {
                shotDetailsPresenter.onLoadMoreCommentsRequest()
            }
        }

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)

    @InjectPresenter
    lateinit var shotDetailsPresenter: ShotDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ShotDetailsPresenter {
        val shotId = intent.getLongExtra(KEY_SHOT_ID, -1)
        val presenterModule = ShotDetailsPresenterModule(shotId)
        val presenterComponent = DaggerShotDetailsPresenterComponent.builder()
            .applicationComponent(BubbbleApplication.getComponent())
            .shotDetailsPresenterModule(presenterModule)
            .build()
        return presenterComponent.getPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shot_details)


        toolbar.setNavigationOnClickListener { finish() }
        initViews()
        shotDetailsPresenter.setPermissionsManager(AndroidPermissionsManager(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        shotDetailsPresenter.removePermissionsManager()
    }

    private fun initViews() {

        noNetworkLayout.findViewById<View>(R.id.retry_button)
            .setOnClickListener { shotDetailsPresenter.retryLoading() }
        commentsList.layoutManager = commentsListLayoutManager

        shotImage.setOnClickListener { shotDetailsPresenter.onImageClicked() }
        userProfileLayout.setOnClickListener { shotDetailsPresenter.onShotAuthorProfileClicked() }
        description.setOnLinkClickListener { url: String? ->
            shotDetailsPresenter.onLinkClicked(
                url!!
            )
        }
        description.setOnUserSelectedListener { userId: Long ->
            shotDetailsPresenter.onCommentAuthorClick(
                userId
            )
        }
        hashtagView.addOnTagClickListener { item: Any? ->
            val tag = item as String?
            shotDetailsPresenter.onTagClicked(tag!!)
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
        }
        likesCount.setOnClickListener { shotDetailsPresenter.onLikeShotClicked() }
        shareShotButton.setOnClickListener { shotDetailsPresenter.onShareShotClicked() }
    }

    override fun showShot(shot: Shot) {
        shotDetailContainer.visibility = View.VISIBLE

        //shot info
        title.text = shot.title
        shotCreateDate.text = dateFormat.format(shot.createdAt)
        if (shot.description != null) {
            description.setHtmlText(shot.description)
        } else {
            description.visibility = View.GONE
        }
        Glide.with(this)
            .load(shot.images.best())
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .centerCrop()
            .crossFade()
            .listener(object : RequestListener<String?, GlideDrawable?> {
                override fun onException(
                    e: Exception,
                    model: String?,
                    target: Target<GlideDrawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    shotDetailsPresenter.onImageLoadError()
                    return false
                }

                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: Target<GlideDrawable?>,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    shotDetailsPresenter.onImageLoadSuccess()
                    return false
                }
            })
            .into(shotImage)
        likesCount.text =
            resources.getQuantityString(R.plurals.likes, shot.likesCount, shot.likesCount)
        viewsCount.text =
            resources.getQuantityString(R.plurals.views, shot.viewsCount, shot.viewsCount)
        bucketsCount.text =
            resources.getQuantityString(R.plurals.buckets, shot.bucketsCount, shot.bucketsCount)
        hashtagView.setData(shot.tags)

        //user info
        userName.text = shot.user.name
        Glide.with(this)
            .load(shot.user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(GlideCircleTransform(this))
            .into(userAvatar)

        //comments
        commentsAdapter.setOnLinkClickListener { url: String? ->
            shotDetailsPresenter.onLinkClicked(
                url!!
            )
        }
        commentsAdapter.setOnUserSelectedListener { userId: Long ->
            shotDetailsPresenter.onCommentAuthorClick(
                userId
            )
        }
        commentsList.adapter = commentsAdapter
        if (shot.commentsCount > 0) {
            commentsList.addOnScrollListener(endlessRecyclerOnScrollListener)
        }
        showToolbarMenu()
    }

    private fun showToolbarMenu() {
        toolbar.inflateMenu(R.menu.shot_details)
        toolbar.setOnMenuItemClickListener { item: MenuItem -> onToolbarItemSelected(item) }
    }

    private fun onToolbarItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.download_shot_image -> {
                shotDetailsPresenter.onDownloadImageClicked()
                true
            }
            R.id.open_in_browser -> {
                shotDetailsPresenter.onOpenShotInBrowserClicked()
                true
            }
            else -> false
        }
    }

    override fun showLoadingProgress() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        loadingLayout.visibility = View.GONE
    }

    override fun showNewComments(newComments: List<Comment>) {
        commentsAdapter.addItems(newComments)
    }

    override fun showCommentsLoadingProgress() {
        commentsAdapter.showLoadingIndicator()
    }

    override fun hideCommentsLoadingProgress() {
        commentsAdapter.hideLoadingIndicator()
    }

    override fun showNoComments() {
        commentsAdapter.setNoComments()
    }

    override fun showImageSavedMessage() {
        Snackbar.make(
            shotDetailContainer!!,
            R.string.image_saved_to_downloads_folder,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun showStorageAccessRationaleMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_title)
            .setMessage(R.string.storage_access_rationale_message)
            .setPositiveButton(R.string.storage_access_ok_button) { dialog: DialogInterface?, which: Int -> shotDetailsPresenter.onDownloadImageClicked() }
            .show()
    }

    override fun showAllowStorageAccessMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_title)
            .setMessage(R.string.storage_access_message)
            .setPositiveButton(R.string.storage_access_settings_button) { dialog: DialogInterface?, which: Int -> shotDetailsPresenter.onAppSettingsButtonClicked() }
            .show()
    }

    override fun openAppSettingsScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun showShotSharing(shotTitle: String, shotUrl: String) {
        AppUtils.sharePlainText(this, String.format("%s - %s", shotTitle, shotUrl))
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(this, url)
    }

    override fun openUserProfileScreen(userId: Long) {
        startActivity(UserProfileActivity.buildIntent(this, userId))
    }

    override fun openShotImageScreen(shot: Shot) {
        startActivity(ShotZoomActivity.buildIntent(this, shot))
    }

    override fun showNoNetworkLayout() {
        noNetworkLayout.visibility = View.VISIBLE
    }

    override fun hideNoNetworkLayout() {
        noNetworkLayout.visibility = View.GONE
    }

    override fun hideImageLoadingProgress() {
        shotImageProgressBar.visibility = View.GONE
    }

    companion object {
        private const val KEY_SHOT_ID = "shot_id"
        fun buildIntent(context: Context?, shotId: Long): Intent {
            val intent = Intent(context, ShotDetailsActivity::class.java)
            intent.putExtra(KEY_SHOT_ID, shotId)
            return intent
        }
    }
}