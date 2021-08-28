package com.imangazalievm.bubbble.presentation.shotdetails

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.Comment
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams
import com.imangazalievm.bubbble.domain.shotdetails.ShotDetailsInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.permissions.Permission
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionResult
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManagerHolder
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import javax.inject.Inject

@InjectViewState
class ShotDetailsPresenter @Inject constructor(
    private val shotDetailsInteractor: ShotDetailsInteractor,
    private val schedulersProvider: SchedulersProvider,
    private val shotId: Long
) : BasePresenter<ShotDetailsView>() {

    private var permissionsManagerHolder = PermissionsManagerHolder()
    private lateinit var shot: Shot
    private val isShotLoaded: Boolean
        get() = ::shot.isInitialized
    private var currentMaxCommentsPage = 1
    private var isCommentsLoading = false

    fun setPermissionsManager(permissionsManager: PermissionsManager) {
        permissionsManagerHolder.setPermissionsManager(permissionsManager)
    }

    fun removePermissionsManager() {
        permissionsManagerHolder.removePermissionsManager()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadShot()
    }

    private fun loadShot() {
        viewState.showLoadingProgress()
        shotDetailsInteractor.getShot(shotId)
            .observeOn(schedulersProvider.ui())
            .subscribe({ shot: Shot -> onShotLoaded(shot) }) { throwable: Throwable ->
                onShotLoadError(
                    throwable
                )
            }
    }

    private fun onShotLoaded(shot: Shot) {
        this.shot = shot
        viewState.hideLoadingProgress()
        viewState.showShot(shot)
        if (shot.commentsCount > 0) {
            loadMoreComments(1)
        } else {
            viewState.showNoComments()
        }
    }

    private fun onShotLoadError(throwable: Throwable) {
        if (throwable is NoNetworkException) {
            viewState.hideLoadingProgress()
            viewState.showNoNetworkLayout()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

    fun onImageLoadError() {
        viewState.hideImageLoadingProgress()
    }

    fun onImageLoadSuccess() {
        viewState.hideImageLoadingProgress()
    }

    fun retryLoading() {
        viewState.hideNoNetworkLayout()
        loadShot()
    }

    private fun loadMoreComments(page: Int) {
        isCommentsLoading = true
        val shotCommentsRequestParams = ShotCommentsRequestParams(shotId, page, COMMENTS_PAGE_SIZE)
        shotDetailsInteractor.getShotComments(shotCommentsRequestParams)
            .observeOn(schedulersProvider.ui())
            .subscribe({ newComments: List<Comment> -> onCommentsLoaded(newComments) }) { throwable: Throwable ->
                DebugUtils.showDebugErrorMessage(
                    throwable
                )
            }
    }

    private fun onCommentsLoaded(newComments: List<Comment>) {
        isCommentsLoading = false
        viewState.hideCommentsLoadingProgress()
        viewState.showNewComments(newComments)
    }

    fun onLoadMoreCommentsRequest() {
        if (isCommentsLoading) {
            return
        }
        currentMaxCommentsPage++
        loadMoreComments(currentMaxCommentsPage)
    }

    fun onImageClicked() {
        viewState.openShotImageScreen(shot)
    }

    fun onLikeShotClicked() {}

    fun onShareShotClicked() {
        if (!isShotLoaded) return
        viewState.showShotSharing(shot.title, shot.htmlUrl)
    }

    fun onDownloadImageClicked() {
        if (!isShotLoaded) return
        if (permissionsManagerHolder.checkPermissionGranted(Permission.READ_EXTERNAL_STORAGE)) {
            saveShotImage()
        } else {
            permissionsManagerHolder.requestPermission(Permission.READ_EXTERNAL_STORAGE) { permissionResult: PermissionResult ->
                if (permissionResult.granted) {
                    saveShotImage()
                } else if (permissionResult.shouldShowRequestPermissionRationale) {
                    viewState.showStorageAccessRationaleMessage()
                } else {
                    viewState.showAllowStorageAccessMessage()
                }
            }
        }
    }

    fun onAppSettingsButtonClicked() {
        viewState.openAppSettingsScreen()
    }

    private fun saveShotImage() {
        if (!isShotLoaded) return
        shotDetailsInteractor.saveImage(shot.images.best())
            .observeOn(schedulersProvider.ui())
            .subscribe({ viewState.showImageSavedMessage() }) { throwable: Throwable ->
                DebugUtils.showDebugErrorMessage( throwable)
            }
    }

    fun onOpenShotInBrowserClicked() {
        viewState.openInBrowser(shot.htmlUrl)
    }

    fun onShotAuthorProfileClicked() {
        viewState.openUserProfileScreen(shot.user.id)
    }

    fun onCommentAuthorClick(userId: Long) {
        viewState.openUserProfileScreen(userId)
    }

    fun onLinkClicked(url: String) {
        viewState.openInBrowser(url)
    }

    fun onTagClicked(tag: String) {}

    companion object {
        private const val COMMENTS_PAGE_SIZE = 20
    }

}