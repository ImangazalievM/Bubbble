package com.imangazalievm.bubbble.presentation.shotzoom

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.shotzoom.ShotZoomInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.permissions.Permission
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionResult
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManagerHolder
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class ShotZoomPresenter @Inject constructor(
    private val shotZoomInteractor: ShotZoomInteractor,
    private val schedulersProvider: SchedulersProvider,
    @Named("shot_title")
    private val shotTitle: String,
    @Named("shot_url")
    private val shotUrl: String,
    @Named("image_url")
    private val imageUrl: String
) : BasePresenter<ShotZoomView>() {

    private val permissionsManagerHolder: PermissionsManagerHolder = PermissionsManagerHolder()

    fun setPermissionsManager(permissionsManager: PermissionsManager?) {
        permissionsManagerHolder.setPermissionsManager(permissionsManager)
    }

    fun removePermissionsManager() {
        permissionsManagerHolder.removePermissionsManager()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showShot()
    }

    private fun showShot() {
        viewState!!.showLoadingProgress()
        viewState!!.showShotImage(imageUrl)
    }

    fun onImageLoadSuccess() {
        viewState!!.hideLoadingProgress()
    }

    fun onImageLoadError() {
        viewState!!.hideLoadingProgress()
        viewState!!.showErrorLayout()
    }

    fun onDownloadImageClicked() {
        if (permissionsManagerHolder.checkPermissionGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage()
        } else {
            permissionsManagerHolder.requestPermission(Permission.READ_EXTERNAL_STORAGE) { permissionResult: PermissionResult ->
                if (permissionResult.granted) {
                    saveShotImage()
                } else if (permissionResult.shouldShowRequestPermissionRationale) {
                    viewState!!.showStorageAccessRationaleMessage()
                } else {
                    viewState!!.showAllowStorageAccessMessage()
                }
            }
        }
    }

    fun onAppSettingsButtonClicked() {
        viewState!!.openAppSettingsScreen()
    }

    private fun saveShotImage() {
        shotZoomInteractor.saveImage(imageUrl)
            .observeOn(schedulersProvider.ui())
            .subscribe({ viewState!!.showImageSavedMessage() }) {
                DebugUtils.showDebugErrorMessage(it)
            }
    }

    fun onOpenInBrowserClicked() {
        viewState!!.openInBrowser(shotUrl)
    }

    fun onShareShotClicked() {
        viewState!!.showShotSharing(shotTitle, shotUrl)
    }

}