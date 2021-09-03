package com.bubbble.presentation.userprofile.followers

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bubbble.models.Follow
import com.bubbble.presentation.global.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserFollowersView : BaseMvpView {

    fun showNewFollowers(newFollowers: List<com.bubbble.models.Follow>)

    fun showFollowersLoadingProgress()

    fun hideFollowersLoadingProgress()

    fun showFollowersLoadingMoreProgress()

    fun hideFollowersLoadingMoreProgress()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openUserDetailsScreen(userId: Long)

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun showLoadMoreError()

}