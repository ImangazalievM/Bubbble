package com.bubbble.shots.shotslist

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.shots.api.ShotsNavigationFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import moxy.InjectViewState

@InjectViewState
class ShotsPresenter @AssistedInject constructor(
    private val shotsRepository: ShotsRepository,
    private val navigationFactory: ShotsNavigationFactory,
    @Assisted private val shotsSort: ShotsFeedParams.Sorting
) : BasePresenter<ShotsView>() {

    override fun onFirstViewAttach() {
        loadShots()
    }

    private fun loadShots() = launchSafe {
        val params = ShotsFeedParams(shotsSort)
        shotsRepository.getShots(params).collectLatest { pagingData ->
            viewState.showPagingData(pagingData)
        }
    }

    fun retryLoading() {
        viewState.retryLoading()
    }

    fun onShotClick(shot: Shot) {
        router.navigateTo(navigationFactory.shotDetailsScreen(shot.id))
    }

    fun onListStateChanged(loadState: CombinedLoadStates) {
        viewState.updateListState(
            isProgressBarVisible = loadState.refresh is LoadState.Loading,
            isRetryVisible = loadState.refresh is LoadState.Error,
            isErrorMsgVisible = loadState.refresh is LoadState.Error
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(shotsSort: ShotsFeedParams.Sorting): ShotsPresenter
    }
}