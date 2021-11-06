package com.bubbble.shotdetails

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotDetailsView : BaseMvpView {

    fun showShot(shot: Shot)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun hideImageLoadingProgress()

    fun showNewComments(newComments: List<Comment>)

    fun showCommentsLoadingProgress()

    fun hideCommentsLoadingProgress()

    fun showNoComments()

    fun showImageSavedMessage()

    fun showStorageAccessRationaleMessage()

    fun showAllowStorageAccessMessage()

    fun openAppSettingsScreen()

    @OneExecution
    fun showShotSharing(shotTitle: String, shotUrl: String)

    @OneExecution
    fun openInBrowser(url: String)

    @OneExecution
    fun openUserProfileScreen(userId: Long)

    @OneExecution
    fun openShotImageScreen(shot: Shot)

}