package com.bubbble.shots.shotslist

import android.util.Log
import moxy.InjectViewState
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.network.NoNetworkException
import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.shots.api.ShotsNavigationFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@InjectViewState
class ShotsPresenter @AssistedInject constructor(
    private val shotsInteractor: ShotsInteractor,
    private val navigationFactory: ShotsNavigationFactory,
    @Assisted private val shotsSort: ShotsFeedParams.Sorting
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
        val shotsRequestParams = ShotsFeedParams(shotsSort, page, PAGE_SIZE)
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
            throwable.printStackTrace()
            Log.d("Bubbble", "shots loading error", throwable)
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
        router.navigateTo(navigationFactory.shotDetailsScreen(shots[position].id))
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_PAGE_NUMBER = 25
    }

    @AssistedFactory
    interface Factory {
        fun create(shotsSort: ShotsFeedParams.Sorting): ShotsPresenter
    }


}