package com.imangazalievm.bubbble.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.models.Shot;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSearchScreen(String searchQuery);

}
