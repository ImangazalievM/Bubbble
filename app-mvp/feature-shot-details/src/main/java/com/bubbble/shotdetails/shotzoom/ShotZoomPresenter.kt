package com.bubbble.shotdetails.shotzoom

import com.afollestad.assent.Permission
import moxy.InjectViewState
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.data.images.ImagesRepository
import com.bubbble.shotdetails.api.ShotDetailsNavigationFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
internal class ShotZoomPresenter @AssistedInject constructor(
    private val imagesRepository: ImagesRepository,
    private val permissionsManager: PermissionsManager,
    private val navigationFactory: ShotDetailsNavigationFactory,
    @Assisted("shotTitle") private val shotTitle: String,
    @Assisted("shotUrl") private val shotUrl: String,
    @Assisted("imageUrl") private val imageUrl: String
) : BasePresenter<ShotZoomView>() {

    override fun onFirstViewAttach() {
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
        router.navigateTo(navigationFactory.appSettingsScreen())
    }

    private fun saveShotImage() = launchSafe {
        imagesRepository.saveImage(imageUrl)
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