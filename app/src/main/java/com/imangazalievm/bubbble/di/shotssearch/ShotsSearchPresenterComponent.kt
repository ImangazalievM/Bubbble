package com.imangazalievm.bubbble.di.shotssearch

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.presentation.shotssearch.ShotsSearchPresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ShotsSearchPresenterModule::class]
)
interface ShotsSearchPresenterComponent {

    fun getPresenter(): ShotsSearchPresenter

}