package com.imangazalievm.bubbble.di.shotslist

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import dagger.Module
import dagger.Provides

@Module
class ShotsPresenterModule(private val sortType: String) {

    @Provides
    @Presenter
    fun provideSortType(): String = sortType

}