package com.bubbble.presentation.userprofile

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.user.User
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserProfileView : BaseMvpView {

    fun showUser(user: User)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    @OneExecution
    fun openUserProfileScreen(userId: Long)

    @OneExecution
    fun openInBrowser(shotUrl: String)

    @OneExecution
    fun showUserProfileSharing(user: User)

    fun closeScreen()

}