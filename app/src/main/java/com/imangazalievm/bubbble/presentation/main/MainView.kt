package com.imangazalievm.bubbble.presentation.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.imangazalievm.bubbble.presentation.global.mvp.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : BaseMvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSearchScreen(searchQuery: String)

}