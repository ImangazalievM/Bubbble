package com.imangazalievm.bubbble.presentation.shotslist

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.data.global.network.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.shotslist.ShotsInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@InjectViewState
class ShotsPresenter @AssistedInject constructor(
    private val shotsInteractor: ShotsInteractor,
    @Assisted private val shotsSort: String
) : BasePresenter<ShotsView>() {

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
        val shotsRequestParams = ShotsRequestParams(shotsSort, page, PAGE_SIZE)
        val newShots = try {
            shotsInteractor.getShots(shotsRequestParams)
        } finally {
            isShotsLoading = false
        }

        if (isFirstLoading) {
            viewState.hideShotsLoadingProgress()
        } else {
            viewState.hideShotsLoadingMoreProgress()
        }
        shots.addAll(newShots)
        viewState.showNewShots(newShots)
    }

    private fun onShotsLoadError(throwable: Throwable) {
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
        if (currentMaxPage < MAX_PAGE_NUMBER) {
            viewState.showShotsLoadingMoreProgress()
            currentMaxPage++
            loadMoreShots(currentMaxPage)
        }
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
        private const val MAX_PAGE_NUMBER = 25
    }

    @AssistedFactory
    interface Factory {
        fun create(shotsSort: String): ShotsPresenter
    }


}