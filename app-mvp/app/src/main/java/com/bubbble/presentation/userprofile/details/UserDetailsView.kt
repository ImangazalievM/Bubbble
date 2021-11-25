package com.bubbble.presentation.userprofile.details

import com.bubbble.core.models.user.User
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface UserDetailsView : BaseMvpView {

    fun showUserInfo(user: User)

    fun showLoadingProgress(isVisible: Boolean)

    fun showNoNetworkLayout(isVisible: Boolean)

    @OneExecution
    fun openInBrowser(url: String)

}