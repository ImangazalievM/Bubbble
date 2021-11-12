package com.bubbble.presentation.shotssearch

import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
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

}