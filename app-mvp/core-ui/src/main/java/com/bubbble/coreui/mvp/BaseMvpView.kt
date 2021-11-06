package com.bubbble.coreui.mvp

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.OneExecution

interface BaseMvpView : MvpView {

    @OneExecution
    fun showMessage(text: String)

    @OneExecution
    fun showMessage(textResId: Int)

}