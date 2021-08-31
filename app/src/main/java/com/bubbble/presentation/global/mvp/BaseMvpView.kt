package com.bubbble.presentation.global.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseMvpView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(text: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(textResId: Int)

}