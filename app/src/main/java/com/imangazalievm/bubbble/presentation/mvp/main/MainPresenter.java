package com.imangazalievm.bubbble.presentation.mvp.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.imangazalievm.bubbble.presentation.mvp.main.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public MainPresenter() {
    }

    public void onSearchQuery(String searchQuery) {
        getViewState().openSearchScreen(searchQuery);
    }

}
