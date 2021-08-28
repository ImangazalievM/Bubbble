package com.imangazalievm.bubbble.presentation.userprofile.details

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.imangazalievm.bubbble.domain.global.models.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : MvpView {

    fun showUserInfo(user: User)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openInBrowser(url: String)

}