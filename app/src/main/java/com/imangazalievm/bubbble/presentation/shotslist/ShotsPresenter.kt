package com.imangazalievm.bubbble.presentation.shotslist

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.shotslist.ShotsInteractor
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import com.imangazalievm.bubbble.presentation.global.utils.DebugUtils
import java.util.*
import javax.inject.Inject

@InjectViewState
class ShotsPresenter @Inject constructor(
    private val shotsInteractor: ShotsInteractor,
    private val schedulersProvider: SchedulersProvider,
    private val shotsSort: String
) : BasePresenter<ShotsView>() {
    
    private var currentMaxPage = 1
    private val shots: MutableList<Shot> = ArrayList()
    private var isShotsLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showShotsLoadingProgress()
        loadMoreShots(currentMaxPage)
    }

    private fun loadMoreShots(page: Int) {
        isShotsLoading = true
        val shotsRequestParams = ShotsRequestParams(shotsSort, page, PAGE_SIZE)
        shotsInteractor.getShots(shotsRequestParams)
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

    private val isFirstLoading: Boolean
        private get() = currentMaxPage == 1

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

}