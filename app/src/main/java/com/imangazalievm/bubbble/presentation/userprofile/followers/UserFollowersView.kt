package com.imangazalievm.bubbble.presentation.userprofile.followers

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.imangazalievm.bubbble.domain.global.models.Follow
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserFollowersView : MvpView {

    fun showNewFollowers(newFollowers: List<Follow>)

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