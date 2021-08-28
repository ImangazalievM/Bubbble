package com.imangazalievm.bubbble.presentation.shotslist

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotsView : MvpView {

    fun showNewShots(newShots: List<Shot>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showShotsLoadingProgress()

    fun hideShotsLoadingProgress()

    fun showShotsLoadingMoreProgress()

    fun hideShotsLoadingMoreProgress()

    fun openShotDetailsScreen(shotId: Long)

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showLoadMoreError()

}