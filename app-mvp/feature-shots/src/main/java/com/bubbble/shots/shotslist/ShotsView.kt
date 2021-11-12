package com.bubbble.shots.shotslist

import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface ShotsView : BaseMvpView {

    fun showNewShots(newShots: List<Shot>)

    @OneExecution
    fun showShotsLoadingProgress()

    fun hideShotsLoadingProgress()

    fun showShotsLoadingMoreProgress()

    fun hideShotsLoadingMoreProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showLoadMoreError()

}