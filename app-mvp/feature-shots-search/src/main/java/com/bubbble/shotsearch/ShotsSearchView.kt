package com.bubbble.shotsearch

import androidx.paging.PagingData
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.mvp.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ShotsSearchView : BaseMvpView {

    fun showPagingData(pagingData: PagingData<Shot>)

    fun updateListState(
        isProgressBarVisible: Boolean,
        isRetryVisible: Boolean,
        isErrorMsgVisible: Boolean
    )

    fun retryLoading()
}