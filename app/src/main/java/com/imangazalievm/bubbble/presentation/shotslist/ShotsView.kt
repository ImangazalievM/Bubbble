package com.imangazalievm.bubbble.presentation.shotslist

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.presentation.global.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotsView : BaseMvpView {

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