package com.imangazalievm.bubbble.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.Shot;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShotDetailsView extends MvpView {

    void showShot(Shot shot);
    void showLoadingProgress();
    void hideLoadingProgress();
    void showNoNetworkLayout();
    void hideNoNetworkLayout();
    void hideImageLoadingProgress();
    void showNewComments(List<Comment> newComments);
    void showCommentsLoadingProgress();
    void hideCommentsLoadingProgress();
    void showNoComments();
    void showImageSavedMessage();
    void showStorageAccessRationaleMessage();
    void showAllowStorageAccessMessage();
    void openAppSettingsScreen();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showShotSharing(String shotTitle, String shotUrl);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openInBrowser(String url);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openUserProfileScreen(long userId);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openShotImageScreen(Shot shot);

}
