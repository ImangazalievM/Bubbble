package com.imangazalievm.bubbble.presentation.shotssearch

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.presentation.global.mvp.BaseMvpView

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

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openShotDetailsScreen(shotId: Long)

}