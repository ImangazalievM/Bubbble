package com.imangazalievm.bubbble.presentation.userprofile.shots

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.data.global.network.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
import com.imangazalievm.bubbble.domain.userprofile.UserShotsInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import java.util.*
import javax.inject.Inject

@InjectViewState
class UserShotsPresenter @Inject constructor(
    private val userShotsInteractor: UserShotsInteractor,
    private val userId: Long
) : BasePresenter<UserShotsView>() {

    private var currentMaxPage = 1
    private val shots: MutableList<Shot> = ArrayList()
    private var isShotsLoading = false
    private val isFirstLoading: Boolean
        private get() = currentMaxPage == 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showShotsLoadingProgress()
        loadMoreShots(currentMaxPage)
    }

    private fun loadMoreShots(page: Int) = launchSafe {
        isShotsLoading = true
        val userShotsRequestParams = UserShotsRequestParams(userId, page, PAGE_SIZE)
        try {
            val newShots = userShotsInteractor.getUserShots(userShotsRequestParams)
            shots.addAll(newShots)
            viewState.showNewShots(newShots)
        } catch (throwable: NoNetworkException) {
            if (isFirstLoading) {
                viewState.showNoNetworkLayout()
            } else {
                viewState.showLoadMoreError()
            }
        } finally {
            viewState.hideShotsLoadingProgress()
        }
        isShotsLoading = false
        if (isFirstLoading) {
            viewState.hideShotsLoadingProgress()
        } else {
            viewState.hideShotsLoadingMoreProgress()
        }
    }

    fun onLoadMoreShotsRequest() {
        if (isShotsLoading) {
            return
        }
        viewState.showShotsLoadingMoreProgress()
        currentMaxPage++
        loadMoreShots(currentMaxPage)
    }

    fun retryLoading() {
        if (isFirstLoading) {
            viewState.hideNoNetworkLayout()
            viewState.showShotsLoadingProgress()
        } else {
            viewState.showShotsLoadingMoreProgress()
        }
        loadMoreShots(currentMaxPage)
    }

    fun onShotClick(position: Int) {
        viewState.openShotDetailsScreen(shots[position].id)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}