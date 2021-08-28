package com.imangazalievm.bubbble.presentation.main

import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSearchScreen(searchQuery: String)

}