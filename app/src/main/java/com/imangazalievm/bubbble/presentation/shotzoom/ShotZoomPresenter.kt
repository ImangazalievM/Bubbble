package com.imangazalievm.bubbble.presentation.shotzoom

import com.afollestad.assent.Permission
import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.shotzoom.ShotZoomInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class ShotZoomPresenter @Inject constructor(
    private val shotZoomInteractor: ShotZoomInteractor,
    private val permissionsManager: PermissionsManager,
    @Named("shot_title")
    private val shotTitle: String,
    @Named("shot_url")
    private val shotUrl: String,
    @Named("image_url")
    private val imageUrl: String
) : BasePresenter<ShotZoomView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showShot()
    }

    private fun showShot() {
        viewState.showLoadingProgress()
        viewState.showShotImage(imageUrl)
    }

    fun onImageLoadSuccess() {
        viewState.hideLoadingProgress()
    }

    fun onImageLoadError() {
        viewState.hideLoadingProgress()
        viewState.showErrorLayout()
    }

    fun onDownloadImageClicked() = launchSafe {
        if (permissionsManager.isGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage()
        } else {
            val result = permissionsManager.requestPermission(Permission.READ_EXTERNAL_STORAGE)
            when {
                result.isGranted -> saveShotImage()
                result.isBlockedFromAsking -> viewState.showStorageAccessRationaleMessage()
                else -> viewState.showAllowStorageAccessMessage()
            }
        }
    }

    fun onAppSettingsButtonClicked() {
        viewState.openAppSettingsScreen()
    }

    private fun saveShotImage() = launchSafe {
        shotZoomInteractor.saveImage(imageUrl)
        viewState.showImageSavedMessage()
    }

    fun onOpenInBrowserClicked() {
        viewState.openInBrowser(shotUrl)
    }

    fun onShareShotClicked() {
        viewState.showShotSharing(shotTitle, shotUrl)
    }

}