package com.bubbble.presentation.shotdetails

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.Shot
import com.bubbble.presentation.global.mvp.BaseMvpView

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

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showShotSharing(shotTitle: String, shotUrl: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openInBrowser(url: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openUserProfileScreen(userId: Long)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openShotImageScreen(shot: Shot)

}