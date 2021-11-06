package com.bubbble.shotdetails

import com.afollestad.assent.Permission
import moxy.InjectViewState
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotCommentsParams
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.coreui.permissions.PermissionsManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
class ShotDetailsPresenter @AssistedInject constructor(
    private val shotDetailsInteractor: ShotDetailsInteractor,
    private val permissionsManager: PermissionsManager,
    @Assisted private val shotId: Long
) : BasePresenter<ShotDetailsView>() {

    private lateinit var shot: Shot
    private val isShotLoaded: Boolean
        get() = ::shot.isInitialized
    private var currentMaxCommentsPage = 1
    private var isCommentsLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadShot()
    }

    private fun loadShot() = launchSafe {
        try {
            viewState.showLoadingProgress()
            this@ShotDetailsPresenter.shot = shotDetailsInteractor.getShot(shotId)
            viewState.hideLoadingProgress()
            viewState.showShot(shot)
            if (shot.commentsCount > 0) {
                loadMoreComments(1)
            } else {
                viewState.showNoComments()
            }
        } finally {
            viewState.hideLoadingProgress()
            viewState.showNoNetworkLayout()
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

    private fun loadMoreComments(page: Int) = launchSafe {
        isCommentsLoading = true
        val shotCommentsRequestParams = ShotCommentsParams(shotId, page, COMMENTS_PAGE_SIZE)
        val newComments = try {
            shotDetailsInteractor.getShotComments(shotCommentsRequestParams)
        } finally {
            isCommentsLoading = false
        }
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
        viewState.showShotSharing(shot.title, shot.shotUrl)
    }

    fun onDownloadImageClicked() = launchSafe {
        if (!isShotLoaded) return@launchSafe
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
        if (!isShotLoaded) return@launchSafe
        shotDetailsInteractor.saveImage(shot.shotUrl)
        viewState.showImageSavedMessage()
    }

    fun onOpenShotInBrowserClicked() {
        viewState.openInBrowser(shot.shotUrl)
    }

    fun onShotAuthorProfileClicked() {
        TODO()
        //viewState.openUserProfileScreen(shot.user.id)
    }

    fun onUserClick(userId: Long) {
        viewState.openUserProfileScreen(userId)
    }

    fun onLinkClicked(url: String) {
        viewState.openInBrowser(url)
    }

    fun onTagClicked(tag: String) {}

    companion object {
        private const val COMMENTS_PAGE_SIZE = 20
    }

    @AssistedFactory
    interface Factory {
        fun create(shotId: Long): ShotDetailsPresenter
    }

}