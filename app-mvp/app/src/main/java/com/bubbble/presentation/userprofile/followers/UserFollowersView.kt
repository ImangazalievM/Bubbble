package com.bubbble.presentation.userprofile.followers

import com.bubbble.core.models.user.Follow
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserFollowersView : BaseMvpView {

    fun showNewFollowers(newFollowers: List<Follow>)

    fun showFollowersLoadingProgress()

    fun hideFollowersLoadingProgress()

    fun showFollowersLoadingMoreProgress()

    fun hideFollowersLoadingMoreProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showLoadMoreError()

}