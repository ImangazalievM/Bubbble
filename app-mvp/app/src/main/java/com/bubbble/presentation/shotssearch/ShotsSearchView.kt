package com.bubbble.presentation.shotssearch

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotsSearchView : BaseMvpView {

    fun showNewShots(newShots: List<Shot>)

    fun clearShotsList()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showShotsLoadingProgress()

    fun hideShotsLoadingProgress()

    fun showShotsLoadingMoreProgress()

    fun hideShotsLoadingMoreProgress()

    fun showLoadMoreError()

    @OneExecution
    fun openShotDetailsScreen(shotId: Long)

}