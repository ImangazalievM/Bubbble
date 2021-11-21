package com.bubbble.presentation.shotssearch

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bubbble.core.models.search.SearchParams
import com.bubbble.core.models.search.SearchType
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotSortType
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.shotdetails.api.ShotDetailsScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import moxy.InjectViewState

@InjectViewState
class ShotsSearchPresenter @AssistedInject constructor(
    private val shotsRepository: ShotsRepository,
    @Assisted private var searchQuery: String
) : BasePresenter<ShotsSearchView>() {

    private val shotsSort: ShotSortType = ShotSortType.POPULAR

    override fun onFirstViewAttach() {

        loadShots()
    }

    fun onNewSearchQuery(searchQuery: String) {
        this.searchQuery = searchQuery
        loadShots()
    }

    fun onListStateChanged(loadState: CombinedLoadStates) {
        viewState.updateListState(
            isProgressBarVisible = loadState.refresh is LoadState.Loading,
            isRetryVisible = loadState.refresh is LoadState.Error,
            isErrorMsgVisible = loadState.refresh is LoadState.Error
        )
    }

    fun retryLoading() {
        viewState.retryLoading()
    }

    fun onShotClick(shot: Shot) {
        router.navigateTo(ShotDetailsScreen(shot.shotSlug))
    }

    private fun loadShots() = launchSafe {
        val requestParams = SearchParams(
            searchQuery = searchQuery,
            searchType = SearchType.SHOT,
            sort = shotsSort,
        )
        shotsRepository.search(requestParams).collectLatest { pagingData ->
            viewState.showPagingData(pagingData)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(searchQuery: String): ShotsSearchPresenter
    }

}