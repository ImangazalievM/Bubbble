package com.imangazalievm.bubbble.di.shotssearch

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import dagger.Module
import dagger.Provides

@Module
class ShotsSearchPresenterModule(private val searchQuery: String) {

    @Provides
    @Presenter
    fun provideSearchQuery(): String = searchQuery

}