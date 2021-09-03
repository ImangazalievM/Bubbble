package com.bubbble.presentation.shotdetails

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bubbble.models.Comment
import com.bubbble.models.Shot
import com.bubbble.presentation.global.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotDetailsView : BaseMvpView {

    fun showShot(shot: com.bubbble.models.Shot)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showNoNetworkLayout()

    fun hideNoNetworkLayout()

    fun hideImageLoadingProgress()

    fun showNewComments(newComments: List<com.bubbble.models.Comment>)

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
    fun openShotImageScreen(shot: com.bubbble.models.Shot)

}