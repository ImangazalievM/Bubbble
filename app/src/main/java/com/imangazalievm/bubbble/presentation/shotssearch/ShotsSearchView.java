package com.imangazalievm.bubbble.presentation.shotssearch;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.global.models.Shot;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShotsSearchView extends MvpView {

    void showNewShots(List<Shot> newShots);
    void clearShotsList();
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void showShotsLoadingProgress();
    void hideShotsLoadingProgress();
    void showShotsLoadingMoreProgress();
    void hideShotsLoadingMoreProgress();
    void showLoadMoreError();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openShotDetailsScreen(long shotId);

}
