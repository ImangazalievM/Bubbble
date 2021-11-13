package com.bubbble.shots.shotslist

import androidx.paging.PagingData
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface ShotsView : BaseMvpView {

    fun showPagingData(pagingData: PagingData<Shot>)

    fun updateListState(
        isProgressBarVisible: Boolean,
        isRetryVisible: Boolean,
        isErrorMsgVisible: Boolean
    )

    fun retryLoading()

}