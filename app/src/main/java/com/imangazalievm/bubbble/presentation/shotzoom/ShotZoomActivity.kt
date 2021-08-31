package com.imangazalievm.bubbble.presentation.shotzoom

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.snackbar.Snackbar
import com.imangazalievm.bubbble.R
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.presentation.global.ui.base.BaseMvpActivity
import com.imangazalievm.bubbble.presentation.global.utils.AppUtils
import javax.inject.Inject

class ShotZoomActivity : BaseMvpActivity(), ShotZoomView {

    override val layoutRes: Int = R.layout.activity_shot_zoom

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }
    private val shotImage: PhotoView by lazy {
        findViewById(R.id.shot_image)
    }
    private val shotZoomContainer: ViewGroup by lazy {
        findViewById(R.id.shot_zoom_container)
    }
    private val loadingLayout: View by lazy {
        findViewById(R.id.loading_layout)
    }
    private val errorLayout: View by lazy {
        findViewById(R.id.error_layout)
    }

    @Inject
    lateinit var presenterFactory: ShotZoomPresenter.Factory

    @InjectPresenter
    lateinit var presenter: ShotZoomPresenter

    @ProvidePresenter
    fun providePresenter(): ShotZoomPresenter {
        val shotTitle = intent.getStringExtra(KEY_SHOT_TITLE)!!
        val imageUrl = intent.getStringExtra(KEY_IMAGE_URL)!!
        val shotUrl = intent.getStringExtra(KEY_SHOT_URL)!!
        return presenterFactory.create(
            shotTitle = shotTitle,
            shotUrl = shotUrl,
            imageUrl = imageUrl
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initViews() {
        errorLayout.findViewById<View>(R.id.open_in_browser_button)
            .setOnClickListener { presenter.onOpenInBrowserClicked() }
    }

    override fun showShotImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .crossFade()
            .listener(object : RequestListener<String?, GlideDrawable?> {
                override fun onException(
                    e: Exception,
                    model: String?,
                    target: Target<GlideDrawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    presenter.onImageLoadError()
                    return false
                }

                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: Target<GlideDrawable?>,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    presenter.onImageLoadSuccess()
                    return false
                }
            })
            .into(shotImage)
        showToolbarMenu()
    }

    override fun showLoadingProgress() {
        loadingLayout.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        loadingLayout.visibility = View.GONE
    }

    private fun showToolbarMenu() {
        toolbar.inflateMenu(R.menu.shot_zoom)
        toolbar.setOnMenuItemClickListener { item: MenuItem -> onToolbarItenSelected(item) }
    }

    private fun onToolbarItenSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.download_shot_image -> {
                presenter.onDownloadImageClicked()
                true
            }
            R.id.share_shot -> {
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
        errorLayout.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        errorLayout.visibility = View.GONE
    }

    override fun showImageSavedMessage() {
        Snackbar.make(
            shotZoomContainer,
            R.string.image_saved_to_downloads_folder,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun showStorageAccessRationaleMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_title)
            .setMessage(R.string.storage_access_rationale_message)
            .setPositiveButton(R.string.storage_access_ok_button) { dialog: DialogInterface?, which: Int -> presenter.onDownloadImageClicked() }
            .show()
    }

    override fun showAllowStorageAccessMessage() {
        AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
            .setTitle(R.string.storage_access_title)
            .setMessage(R.string.storage_access_message)
            .setPositiveButton(R.string.storage_access_settings_button) { dialog: DialogInterface?, which: Int -> presenter.onAppSettingsButtonClicked() }
            .show()
    }

    override fun openAppSettingsScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
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

    companion object {

        private const val KEY_SHOT_TITLE = "shot_title"
        private const val KEY_SHOT_URL = "shot_url"
        private const val KEY_IMAGE_URL = "image_url"

        fun buildIntent(context: Context?, shot: Shot): Intent {
            val intent = Intent(context, ShotZoomActivity::class.java)
            intent.putExtra(KEY_SHOT_TITLE, shot.title)
            intent.putExtra(KEY_SHOT_URL, shot.htmlUrl)
            intent.putExtra(KEY_IMAGE_URL, shot.images.best())
            return intent
        }
    }

}