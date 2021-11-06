package com.bubbble.shots.main

import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter @AssistedInject constructor(

) : MvpPresenter<MainView>() {

    fun onSearchQuery(searchQuery: String) {
        viewState.openSearchScreen(searchQuery)
    }

    @AssistedFactory
    interface Factory {
        fun create(): MainPresenter
    }

}