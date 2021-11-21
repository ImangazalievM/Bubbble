package com.bubbble.shotdetails

import com.afollestad.assent.Permission
import moxy.InjectViewState
import com.bubbble.core.models.shot.ShotCommentsParams
import com.bubbble.core.models.shot.ShotDetails
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.data.comments.CommentsRepository
import com.bubbble.data.global.UserUrlParser
import com.bubbble.data.images.ImagesRepository
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.shotdetails.api.ShotDetailsNavigationFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@InjectViewState
internal class ShotDetailsPresenter @AssistedInject constructor(
    private val shotsRepository: ShotsRepository,
    private val commentsRepository: CommentsRepository,
    private val imagesRepository: ImagesRepository,
    private val permissionsManager: PermissionsManager,
    private val navigationFactory: ShotDetailsNavigationFactory,
    private val userUrlParser: UserUrlParser,
    @Assisted private val shotSlug: String
) : BasePresenter<ShotDetailsView>() {

    private lateinit var shot: ShotDetails
    private val isShotLoaded: Boolean
        get() = ::shot.isInitialized
    private var currentMaxCommentsPage = 1
    private var isCommentsLoading = false

    override fun onFirstViewAttach() {

        loadShot()
    }

    private fun loadShot() = launchSafe {
        viewState.showLoadingProgress(true)
        try {
            shot = shotsRepository.getShot(shotSlug)
            viewState.showShot(shot)
            if (shot.commentsCount > 0) {
                loadMoreComments(1)
            } else {
                viewState.showNoComments()
            }
        } catch (error: Exception) {
            viewState.showNoNetworkLayout(true)
        } finally {
            viewState.showLoadingProgress(false)
        }
    }

    fun onImageLoadError() {
        viewState.hideImageLoadingProgress()
    }

    fun onImageLoadSuccess() {
        viewState.hideImageLoadingProgress()
    }

    fun retryLoading() {
        viewState.showNoNetworkLayout(false)
        loadShot()
    }

    private fun loadMoreComments(page: Int) = launchSafe {
        isCommentsLoading = true
        val shotCommentsRequestParams = ShotCommentsParams(shotSlug, page, COMMENTS_PAGE_SIZE)
        val newComments = try {
            commentsRepository.getComments(shotCommentsRequestParams)
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
        router.navigateTo(navigationFactory.shotImageScreen(
            title = shot.title,
            shotUrl  = shot.shotUrl ,
            imageUrl = shot.imageUrl
        ))
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
        router.navigateTo(navigationFactory.appSettingsScreen())
    }

    private fun saveShotImage() = launchSafe {
        if (!isShotLoaded) return@launchSafe
        imagesRepository.saveImage(shot.imageUrl)
        viewState.showImageSavedMessage()
    }

    fun onOpenShotInBrowserClicked() {
        viewState.openInBrowser(shot.shotUrl)
    }

    fun onShotAuthorProfileClicked() {
        router.navigateTo(navigationFactory.userProfileScreen(shot.user.userName))
    }

    fun onUserClick(userProfileUrl: String) {
        val userName = userUrlParser.getUserName(userProfileUrl)
        if (userName != null) {
            router.navigateTo(navigationFactory.userProfileScreen(userName))
        } else {
            //ToDo: handle it
        }
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
        fun create(shotSlug: String): ShotDetailsPresenter
    }

}