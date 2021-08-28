package com.imangazalievm.bubbble.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public MainPresenter() {
    }

    public void onSearchQuery(String searchQuery) {
        getViewState().openSearchScreen(searchQuery);
    }

}
