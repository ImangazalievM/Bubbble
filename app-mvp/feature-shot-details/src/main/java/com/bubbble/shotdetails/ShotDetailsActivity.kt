package com.bubbble.shotdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.ShotDetails
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.ui.commons.glide.GlideCircleTransform
import com.bubbble.coreui.utils.AppUtils
import com.bubbble.shotdetails.api.ShotDetailsScreen
import com.bubbble.shotdetails.comments.ShotCommentsAdapter
import com.bubbble.shotdetails.databinding.ActivityShotDetailsBinding
import com.bubbble.shotdetails.databinding.ItemShotDescriptionBinding
import com.bubbble.ui.extensions.isVisible
import com.bubbble.coreui.navigationargs.getScreenData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
internal class ShotDetailsActivity : BaseMvpActivity(), ShotDetailsView {

    override val layoutRes: Int = R.layout.activity_shot_details

    private val binding: ActivityShotDetailsBinding by viewBinding()
    private val shotDescriptionBinding: ItemShotDescriptionBinding by viewBinding()

    private val commentsAdapter: ShotCommentsAdapter by lazy {
        ShotCommentsAdapter(
            context = this,
            description = shotDescriptionBinding.root,
            onLinkClick = presenter::onLinkClicked,
            onUserUrlClick = { onUserUrlSelected(it) },
            onUserClick = presenter::onUserClick
        )
    }

    private val commentsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    //private val endlessRecyclerOnScrollListener =
    //    object : EndlessRecyclerOnScrollListener(commentsListLayoutManager) {
    //        override fun onLoadMore() {
    //            presenter.onLoadMoreCommentsRequest()
    //        }
    //    }

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)

    @Inject
    lateinit var presenterFactory: ShotDetailsPresenter.Factory

    val presenter by moxyPresenter {
        val shotSlug = getScreenData<ShotDetailsScreen.Data>().shotSlug
        presenterFactory.create(shotSlug)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { finish() }
        initViews()
    }

    private fun initViews() {
        binding.noNetworkLayout.retryButton
            .setOnClickListener { presenter.retryLoading() }
        binding.shotComments.layoutManager = commentsListLayoutManager

        binding.shotImage.setOnClickListener { presenter.onImageClicked() }
        shotDescriptionBinding.userProfileLayout.setOnClickListener { presenter.onShotAuthorProfileClicked() }
        shotDescriptionBinding.shotDescription.setOnLinkClickListener { url: String? ->
            presenter.onLinkClicked(
                url!!
            )
        }
        shotDescriptionBinding.shotDescription.setOnUserSelectedListener(::onUserUrlSelected)
        shotDescriptionBinding.shotTags.addOnTagClickListener { item: Any? ->
            val tag = item as String?
            presenter.onTagClicked(tag!!)
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
        }
        shotDescriptionBinding.likesCount.setOnClickListener { presenter.onLikeShotClicked() }
        shotDescriptionBinding.shareShotButton.setOnClickListener { presenter.onShareShotClicked() }
    }

    override fun showShot(shot: ShotDetails) {
        binding.shotDetailsContainer.visibility = View.VISIBLE

        //shot info
        shotDescriptionBinding.shotTitle.text = shot.title
        //shotCreateDate.text = dateFormat.format(shot.createdAt)
        //ToDo:
        //if (shot.description != null) {
        //    description.setHtmlText(shot.description)
        //} else {
        //    description.visibility = View.GONE
        //}
        Glide.with(this)
            .load(shot.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .centerCrop()
            .crossFade()
            .listener(object : RequestListener<String?, GlideDrawable?> {
                override fun onException(
                    e: Exception,
                    model: String?,
                    target: com.bumptech.glide.request.target.Target<GlideDrawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    presenter.onImageLoadError()
                    return false
                }

                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: com.bumptech.glide.request.target.Target<GlideDrawable?>,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    presenter.onImageLoadSuccess()
                    return false
                }
            })
            .into(binding.shotImage)
        shotDescriptionBinding.likesCount.text =
            resources.getQuantityString(R.plurals.likes, shot.likesCount, shot.likesCount)
        shotDescriptionBinding.viewsCount.text =
            resources.getQuantityString(R.plurals.views, shot.viewsCount, shot.viewsCount)
        //bucketsCount.text =
        //    resources.getQuantityString(R.plurals.buckets, shot.bucketsCount, shot.bucketsCount)
        //hashtagView.setData(shot.tags)

        //user info
        shotDescriptionBinding.userName.text = shot.user.displayName
        Glide.with(this)
            .load(shot.user.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(GlideCircleTransform(this))
            .into(shotDescriptionBinding.userAvatar)

        binding.shotComments.adapter = commentsAdapter
        if (shot.commentsCount > 0) {
            //commentsList.addOnScrollListener(endlessRecyclerOnScrollListener)
        }
        showToolbarMenu()
    }

    private fun showToolbarMenu() {
        binding.toolbar.inflateMenu(R.menu.shot_details)
        binding.toolbar.setOnMenuItemClickListener { item: MenuItem -> onToolbarItemSelected(item) }
    }

    private fun onToolbarItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.download_shot_image -> {
                presenter.onDownloadImageClicked()
                true
            }
            R.id.open_in_browser -> {
                presenter.onOpenShotInBrowserClicked()
                true
            }
            else -> false
        }
    }

    override fun showLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.root.isVisible = isVisible
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
            binding.shotDetailsContainer,
            R.string.image_saved_to_downloads_folder,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun showStorageAccessRationaleMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_error_title)
            .setMessage(R.string.storage_access_rationale_message)
            .setPositiveButton(R.string.storage_access_ok_button) { dialog: DialogInterface?, which: Int -> presenter.onDownloadImageClicked() }
            .show()
    }

    override fun showAllowStorageAccessMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_error_title)
            .setMessage(R.string.storage_access_message)
            .setPositiveButton(R.string.storage_access_settings_button) { dialog: DialogInterface?, which: Int -> presenter.onAppSettingsButtonClicked() }
            .show()
    }

    override fun showShotSharing(shotTitle: String, shotUrl: String) {
        AppUtils.sharePlainText(this, String.format("%s - %s", shotTitle, shotUrl))
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(this, url)
    }

    override fun showNoNetworkLayout(isVisible: Boolean) {
        binding.noNetworkLayout.isVisible = isVisible
    }

    override fun hideImageLoadingProgress() {
        binding.shotImageProgressBar.visibility = View.GONE
    }

    private fun onUserUrlSelected(url: String) {
        presenter.onUserClick(url)
    }
}