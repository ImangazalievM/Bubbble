package com.bubbble.shotdetails

import com.bubbble.core.models.Comment
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
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

    @OneExecution
    fun showShotSharing(shotTitle: String, shotUrl: String)

    @OneExecution
    fun openInBrowser(url: String)

}