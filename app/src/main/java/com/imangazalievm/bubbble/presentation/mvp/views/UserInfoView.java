package com.imangazalievm.bubbble.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.models.User;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserInfoView extends MvpView {

    void showUser(User user);
    void showLoadingProgress();
    void hideLoadingProgress();
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showUserProfileSharing(User user);

}
