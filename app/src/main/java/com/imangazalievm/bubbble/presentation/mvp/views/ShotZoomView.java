package com.imangazalievm.bubbble.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface ShotZoomView extends MvpView {

    void showShotImage(String imageUrl);
    void showLoadingProgress();
    void hideLoadingProgress();
    void showErrorLayout();
    void hideErrorLayout();
    void showImageSavedMessage();
    void showStorageAccessRationaleMessage();
    void showAllowStorageAccessMessage();
    void openAppSettingsScreen();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showShotSharing(String shotTitle, String url);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openInBrowser(String shotUrl);
}
