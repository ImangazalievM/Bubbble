package com.imangazalievm.bubbble.presentation.userprofile.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.global.models.User;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserDetailsView extends MvpView {

    void showUserInfo(User user);
    void showLoadingProgress();
    void hideLoadingProgress();
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openInBrowser(String url);

}
