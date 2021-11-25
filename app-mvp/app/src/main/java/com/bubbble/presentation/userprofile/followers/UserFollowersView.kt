package com.bubbble.presentation.userprofile.followers

import com.bubbble.core.models.user.Follow
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserFollowersView : BaseMvpView {

    fun showNewFollowers(newFollowers: List<Follow>)

    fun showFollowersLoadingProgress(isVisible: Boolean)

    fun showFollowersLoadingMoreProgress(isVisible: Boolean)

    fun showNoNetworkLayout(isVisible: Boolean)

    fun showLoadMoreError()

}