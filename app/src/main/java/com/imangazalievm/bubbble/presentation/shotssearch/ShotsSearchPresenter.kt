package com.imangazalievm.bubbble.presentation.shotssearch

import com.arellomobile.mvp.InjectViewState
import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.data.global.network.exceptions.NoNetworkException
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams
import com.imangazalievm.bubbble.domain.shotssearch.ShotsSearchInteractor
import com.imangazalievm.bubbble.presentation.global.mvp.BasePresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class ShotsSearchPresenter @Inject constructor(
    private val shotsSearchInteractor: ShotsSearchInteractor,
    private var searchQuery: String
) : BasePresenter<ShotsSearchView>() {

    private val sort: String = Constants.SHOTS_SORT_POPULAR
    private val shots: MutableList<Shot> = ArrayList()
    private var currentMaxPage = 1
    private var isShotsLoading = false
    private val isFirstLoading: Boolean
        get() = currentMaxPage == 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startSearch(searchQuery)
    }

    private fun startSearch(searchQuery: String) {
        this.searchQuery = searchQuery
        viewState.showShotsLoadingProgress()
        loadMoreShots(currentMaxPage)
    }

    private fun loadMoreShots(page: Int) = launchSafe {
        try {
            isShotsLoading = true
            val shotsRequestParams = ShotsSearchRequestParams(searchQuery, sort, page, PAGE_SIZE)
            val newShots = shotsSearchInteractor.search(shotsRequestParams)
            if (isFirstLoading) {
                viewState.hideShotsLoadingProgress()
            } else {
                viewState.hideShotsLoadingMoreProgress()
            }
            shots.addAll(newShots)
            viewState.showNewShots(newShots)
        } catch (e: NoNetworkException) {
            if (isFirstLoading) {
                viewState.hideShotsLoadingProgress()
                viewState.showNoNetworkLayout()
            } else {
                viewState.hideShotsLoadingMoreProgress()
                viewState.showLoadMoreError()
            }
        } finally {
            isShotsLoading = false
        }
    }

    fun onNewSearchQuery(searchQuery: String) {
        viewState.clearShotsList()
        shots.clear()
        startSearch(searchQuery)
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

    fun onShotClick(position: Int) {
        viewState.openShotDetailsScreen(shots[position].id)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_PAGE_NUMBER = 25
    }

}