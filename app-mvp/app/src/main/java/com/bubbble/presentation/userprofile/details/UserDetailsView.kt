package com.bubbble.presentation.userprofile.details

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.user.User
import com.bubbble.coreui.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : BaseMvpView {

    fun showUserInfo(user: User)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openInBrowser(url: String)

}