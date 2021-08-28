package com.imangazalievm.bubbble.presentation.shotzoom

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShotZoomView : MvpView {

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