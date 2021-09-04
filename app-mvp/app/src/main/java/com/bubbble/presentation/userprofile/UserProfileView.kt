package com.bubbble.presentation.userprofile

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.user.User
import com.bubbble.coreui.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserProfileView : BaseMvpView {

    fun showUser(user: User)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openUserProfileScreen(userId: Long)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openInBrowser(shotUrl: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showUserProfileSharing(user: User)

    fun closeScreen()

}