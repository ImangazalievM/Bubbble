package com.imangazalievm.bubbble.presentation.userprofile.followers;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.global.models.Follow;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface UserFollowersView extends MvpView {

    void showNewFollowers(List<Follow> newFollowers);
    void showFollowersLoadingProgress();
    void hideFollowersLoadingProgress();
    void showFollowersLoadingMoreProgress();
    void hideFollowersLoadingMoreProgress();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openUserDetailsScreen(long userId);
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void showLoadMoreError();

}
