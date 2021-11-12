package com.bubbble.presentation.shotzoom

import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface ShotZoomView : BaseMvpView {

    fun showShotImage(imageUrl: String)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun showErrorLayout()

    fun hideErrorLayout()

    fun showImageSavedMessage()

    fun showStorageAccessRationaleMessage()

    fun showAllowStorageAccessMessage()

    @OneExecution
    fun showShotSharing(shotTitle: String, url: String)

    @OneExecution
    fun openInBrowser(shotUrl: String)

}