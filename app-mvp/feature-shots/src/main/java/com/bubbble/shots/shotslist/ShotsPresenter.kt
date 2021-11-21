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
        router.navigateTo(navigationFactory.shotDetailsScreen(shot.shotSlug))
    }

    fun onListStateChanged(loadState: CombinedLoadStates) {
        val state = loadState.refresh
        if (state is LoadState.Error) {
            errorHandler.proceed(state.error)
        }
        viewState.updateListState(
            isProgressBarVisible = state is LoadState.Loading,
            isRetryVisible = state is LoadState.Error,
            isErrorMsgVisible = state is LoadState.Error
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(shotsSort: ShotsFeedParams.Sorting): ShotsPresenter
    }
}