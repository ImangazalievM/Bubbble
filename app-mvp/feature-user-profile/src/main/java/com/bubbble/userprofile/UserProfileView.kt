package com.bubbble.userprofile

import com.bubbble.core.models.user.User
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface UserProfileView : BaseMvpView {

    fun showUser(user: User)

    fun showLoadingProgress(isVisible: Boolean)

    fun showNoNetworkLayout(isVisible: Boolean)

    @OneExecution
    fun openInBrowser(shotUrl: String)

    @OneExecution
    fun showUserProfileSharing(user: User)

    fun closeScreen()

}