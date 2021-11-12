package com.bubbble.shots.main

import com.bubbble.coreui.mvp.BasePresenter
import com.bubbble.shots.api.ShotsNavigationFactory
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter @AssistedInject constructor(
    private val navigationFactory: ShotsNavigationFactory
) : BasePresenter<MainView>() {

    fun onSearchQuery(searchQuery: String) {
        router.navigateTo(navigationFactory.shotsSearchScreen(searchQuery))
    }

    @AssistedFactory
    interface Factory {
        fun create(): MainPresenter
    }

}