package com.bubbble.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    fun onSearchQuery(searchQuery: String) {
        viewState.openSearchScreen(searchQuery)
    }

}