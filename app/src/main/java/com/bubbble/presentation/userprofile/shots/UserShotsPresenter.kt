package com.bubbble.presentation.userprofile.shots

import com.arellomobile.mvp.InjectViewState
import com.bubbble.core.exceptions.NoNetworkException
import com.bubbble.domain.global.models.Shot
import com.bubbble.domain.global.models.UserShotsRequestParams
import com.bubbble.domain.userprofile.UserShotsInteractor
import com.bubbble.presentation.global.mvp.BasePresenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@InjectViewState
class UserShotsPresenter @AssistedInject constructor(
    private val userShotsInteractor: UserShotsInteractor,
    @Assisted private val userId: Long
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
        } catch (throwable: com.bubbble.core.exceptions.NoNetworkException) {
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

    @AssistedFactory
    interface Factory {
        fun create(userId: Long): UserShotsPresenter
    }

}