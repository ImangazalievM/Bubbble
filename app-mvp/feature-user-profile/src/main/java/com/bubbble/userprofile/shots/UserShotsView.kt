package com.bubbble.userprofile.shots

import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserShotsView : BaseMvpView {

    fun showNewShots(newShots: List<Shot>)

    fun showShotsLoadingProgress(isVisible: Boolean)

    fun showShotsLoadingMoreProgress(isVisible: Boolean)

    fun showNoNetworkLayout(isVisible: Boolean)

    fun showLoadMoreError()

}