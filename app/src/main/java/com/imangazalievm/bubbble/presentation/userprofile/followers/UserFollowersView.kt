package com.imangazalievm.bubbble.presentation.userprofile.followers

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.imangazalievm.bubbble.domain.global.models.Follow
import com.imangazalievm.bubbble.presentation.global.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserFollowersView : BaseMvpView {

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