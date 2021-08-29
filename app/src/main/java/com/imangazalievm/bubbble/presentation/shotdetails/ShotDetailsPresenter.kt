package com.imangazalievm.bubbble.presentation.shotdetails

import com.afollestad.assent.Permission
import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams
import com.imangazalievm.bubbble.domain.shotdetails.ShotDetailsInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import javax.inject.Inject

@InjectViewState
class ShotDetailsPresenter @Inject constructor(
    private val shotDetailsInteractor: ShotDetailsInteractor,
    private val permissionsManager: PermissionsManager,
    private val shotId: Long
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
        val shotCommentsRequestParams = ShotCommentsRequestParams(shotId, page, COMMENTS_PAGE_SIZE)
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
        viewState.showShotSharing(shot.title, shot.htmlUrl)
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
        shotDetailsInteractor.saveImage(shot.images.best())
        viewState.showImageSavedMessage()
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