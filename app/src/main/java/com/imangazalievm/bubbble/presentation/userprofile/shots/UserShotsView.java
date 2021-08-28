package com.imangazalievm.bubbble.presentation.userprofile.shots;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.global.models.Shot;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserShotsView extends MvpView {

    void showNewShots(List<Shot> newShots);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showShotsLoadingProgress();
    void hideShotsLoadingProgress();
    void showShotsLoadingMoreProgress();
    void hideShotsLoadingMoreProgress();
    void openShotDetailsScreen(long shotId);
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void showLoadMoreError();

}
