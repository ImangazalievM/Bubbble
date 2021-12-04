package com.bubbble.userprofile.shots

import moxy.InjectViewState
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.user.UserShotsParams
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.userprofile.api.UserProfileNavigationFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@InjectViewState
class UserShotsPresenter @AssistedInject constructor(
    private val shotsRepository: ShotsRepository,
    private val navigationFactory: UserProfileNavigationFactory,
    @Assisted private val userName: String
) : BasePresenter<UserShotsView>() {

    private var currentMaxPage = 1
    private val shots: MutableList<Shot> = ArrayList()
    private var isShotsLoading = false
    private val isFirstLoading: Boolean
        get() = currentMaxPage == 1

    override fun onFirstViewAttach() {
        viewState.showShotsLoadingProgress(true)
        loadMoreShots(currentMaxPage)
    }

    private fun loadMoreShots(page: Int) = launchSafe {
        isShotsLoading = true
        val userShotsRequestParams = UserShotsParams(userName, page, PAGE_SIZE)
        try {
            val newShots = shotsRepository.getUserShots(userShotsRequestParams)
            shots.addAll(newShots)
            viewState.showNewShots(newShots)
        } catch (throwable: NoNetworkException) {
            if (isFirstLoading) {
                viewState.showNoNetworkLayout(true)
            } else {
                viewState.showLoadMoreError()
            }
        } finally {
            viewState.showShotsLoadingProgress(true)
        }
        isShotsLoading = false
        if (isFirstLoading) {
            viewState.showShotsLoadingProgress(false)
        } else {
            viewState.showShotsLoadingMoreProgress(false)
        }
    }

    fun onLoadMoreShotsRequest() {
        if (isShotsLoading) {
            return
        }
        viewState.showShotsLoadingMoreProgress(true)
        currentMaxPage++
        loadMoreShots(currentMaxPage)
    }

    fun retryLoading() {
        if (isFirstLoading) {
            viewState.showNoNetworkLayout(false)
            viewState.showShotsLoadingProgress(true)
        } else {
            viewState.showShotsLoadingMoreProgress(true)
        }
        loadMoreShots(currentMaxPage)
    }

    fun onShotClick(shot: Shot) {
        router.navigateTo(navigationFactory.shotDetailsScreen(shot.shotSlug))
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    @AssistedFactory
    interface Factory {
        fun create(userName: String): UserShotsPresenter
    }

}