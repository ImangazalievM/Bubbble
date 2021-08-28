package com.imangazalievm.bubbble.presentation.shotzoom

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.imangazalievm.bubbble.presentation.global.mvp.BaseMvpView

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

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showShotSharing(shotTitle: String, url: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openInBrowser(shotUrl: String)

}