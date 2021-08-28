package com.imangazalievm.bubbble.presentation.userprofile.shots

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.userprofile.UserShotsInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.arellomobile.mvp.MvpPresenter
import com.imangazalievm.bubbble.presentation.userprofile.shots.UserShotsView
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
import com.imangazalievm.bubbble.presentation.userprofile.shots.UserShotsPresenter
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import java.util.ArrayList
import javax.inject.Inject

@InjectViewState
class UserShotsPresenter @Inject constructor(
    private val userShotsInteractor: UserShotsInteractor,
    private val schedulersProvider: SchedulersProvider,
    private val userId: Long
) : MvpPresenter<UserShotsView>() {
    
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

    private fun loadMoreShots(page: Int) {
        isShotsLoading = true
        val userShotsRequestParams = UserShotsRequestParams(userId, page, PAGE_SIZE)
        userShotsInteractor.getUserShots(userShotsRequestParams)
            .observeOn(schedulersProvider.ui())
            .subscribe({ newShots: List<Shot> -> onShotsLoaded(newShots) }) { throwable: Throwable ->
                onShotsLoadError(
                    throwable
                )
            }
    }

    private fun onShotsLoaded(newShots: List<Shot>) {
        isShotsLoading = false
        if (isFirstLoading) {
            viewState.hideShotsLoadingProgress()
        } else {
            viewState.hideShotsLoadingMoreProgress()
        }
        shots.addAll(newShots)
        viewState.showNewShots(newShots)
    }

    private fun onShotsLoadError(throwable: Throwable) {
        isShotsLoading = true
        if (throwable is NoNetworkException) {
            if (isFirstLoading) {
                viewState.hideShotsLoadingProgress()
                viewState.showNoNetworkLayout()
            } else {
                viewState.hideShotsLoadingMoreProgress()
                viewState.showLoadMoreError()
            }
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
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