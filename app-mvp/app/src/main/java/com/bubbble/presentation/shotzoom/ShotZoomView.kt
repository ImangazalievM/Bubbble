package com.bubbble.presentation.shotzoom

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.OneExecution

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotZoomView : BaseMvpView {

    fun showShotImage(imageUrl: String)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showErrorLayout()

    fun hideErrorLayout()

    fun showImageSavedMessage()

    fun showStorageAccessRationaleMessage()

    fun showAllowStorageAccessMessage()

    fun openAppSettingsScreen()

    @OneExecution
    fun showShotSharing(shotTitle: String, url: String)

    @OneExecution
    fun openInBrowser(shotUrl: String)

}