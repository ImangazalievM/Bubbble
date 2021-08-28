package com.imangazalievm.bubbble.presentation.shotssearch

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotsSearchView : MvpView {

    fun showNewShots(newShots: List<Shot>)

    fun clearShotsList()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showShotsLoadingProgress()

    fun hideShotsLoadingProgress()

    fun showShotsLoadingMoreProgress()

    fun hideShotsLoadingMoreProgress()

    fun showLoadMoreError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openShotDetailsScreen(shotId: Long)

}