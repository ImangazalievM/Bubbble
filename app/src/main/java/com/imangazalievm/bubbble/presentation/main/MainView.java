package com.imangazalievm.bubbble.presentation.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSearchScreen(String searchQuery);

}
