package com.bubbble.presentation.shotssearch

import com.bubbble.core.models.search.SearchParams
import com.bubbble.core.models.search.SearchType
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotSortType
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.domain.shotssearch.ShotsSearchInteractor
import com.bubbble.presentation.global.navigation.ShotDetailsScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import moxy.InjectViewState
import java.util.*

@InjectViewState
class ShotsSearchPresenter @AssistedInject constructor(
    private val shotsSearchInteractor: ShotsSearchInteractor,
    @Assisted private var searchQuery: String
) : BasePresenter<ShotsSearchView>() {

    private val sort: ShotSortType = ShotSortType.POPULAR
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
            val shotsRequestParams = SearchParams(
                searchQuery = searchQuery,
                searchType = SearchType.SHOT,
                sort = sort,
                page = page,
                pageSize = PAGE_SIZE

            )
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
        router.navigateTo(ShotDetailsScreen(shots[position].id))
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_PAGE_NUMBER = 25
    }

    @AssistedFactory
    interface Factory {
        fun create(searchQuery: String): ShotsSearchPresenter
    }

}