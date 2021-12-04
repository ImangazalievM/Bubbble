package com.bubbble.shotdetails.shotzoom

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.coreui.ui.base.BaseMvpActivity
import com.bubbble.coreui.utils.AppUtils
import com.bubbble.shotdetails.R
import com.bubbble.shotdetails.api.ShotImageZoomScreen
import com.bubbble.shotdetails.databinding.ActivityShotZoomBinding
import com.bubbble.ui.extensions.isVisible
import com.bubbble.coreui.navigationargs.getScreenData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
internal class ShotZoomActivity : BaseMvpActivity(), ShotZoomView {

    override val layoutRes: Int = R.layout.activity_shot_zoom

    @Inject
    lateinit var presenterFactory: ShotZoomPresenter.Factory

    val presenter by moxyPresenter {
        val data = getScreenData<ShotImageZoomScreen.Data>()
        presenterFactory.create(
            shotTitle = data.title,
            shotUrl = data.shotUrl,
            imageUrl = data.imageUrl
        )
    }

    private val binding: ActivityShotZoomBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initViews() {
        binding.errorLayout.findViewById<View>(R.id.open_in_browser_button)
            .setOnClickListener { presenter.onOpenInBrowserClicked() }
    }

    override fun showShotImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .crossFade()
            //.listener(object : RequestListener<String?, GlideDrawable?> {
            //    override fun onException(
            //        e: Exception,
            //        model: String?,
            //        target: Target<GlideDrawable?>,
            //        isFirstResource: Boolean
            //    ): Boolean {
            //        presenter.onImageLoadError()
            //        return false
            //    }
//
            //    override fun onResourceReady(
            //        resource: GlideDrawable?,
            //        model: String?,
            //        target: Target<GlideDrawable?>,
            //        isFromMemoryCache: Boolean,
            //        isFirstResource: Boolean
            //    ): Boolean {
            //        presenter.onImageLoadSuccess()
            //        return false
            //    }
            //})
            .into(binding.shotImage)
        showToolbarMenu()
    }

    override fun showLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    private fun showToolbarMenu() {
        binding.toolbar.inflateMenu(R.menu.shot_zoom)
        binding.toolbar.setOnMenuItemClickListener { item: MenuItem -> onToolbarItemSelected(item) }
    }

    private fun onToolbarItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.download_shot_image -> {
                presenter.onDownloadImageClicked()
                true
            }
            R.id.shareShotButton-> {
                presenter.onShareShotClicked()
                true
            }
            R.id.open_in_browser -> {
                presenter.onOpenInBrowserClicked()
                true
            }
            else -> false
        }
    }

    override fun showShotSharing(shotTitle: String, shotUrl: String) {
        AppUtils.sharePlainText(this, String.format("%s - %s", shotTitle, shotUrl))
    }

    override fun openInBrowser(url: String) {
        AppUtils.openInChromeTab(this, url)
    }

    override fun showErrorLayout() {
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        binding.errorLayout.visibility = View.GONE
    }

    override fun showImageSavedMessage() {
        Snackbar.make(
            binding.shotZoomContainer,
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}