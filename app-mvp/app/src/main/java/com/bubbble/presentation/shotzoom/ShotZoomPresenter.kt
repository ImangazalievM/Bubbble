package com.bubbble.presentation.shotzoom

import com.afollestad.assent.Permission
import moxy.InjectViewState
import com.bubbble.domain.shotzoom.ShotZoomInteractor
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.presentation.global.navigation.AppSettingsScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class ShotZoomPresenter @AssistedInject constructor(
    private val shotZoomInteractor: ShotZoomInteractor,
    private val permissionsManager: PermissionsManager,
    @Assisted("shotTitle") private val shotTitle: String,
    @Assisted("shotUrl") private val shotUrl: String,
    @Assisted("imageUrl") private val imageUrl: String
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
        router.navigateTo(AppSettingsScreen())
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("shotTitle") shotTitle: String,
            @Assisted("shotUrl") shotUrl: String,
            @Assisted("imageUrl") imageUrl: String
        ): ShotZoomPresenter
    }

}