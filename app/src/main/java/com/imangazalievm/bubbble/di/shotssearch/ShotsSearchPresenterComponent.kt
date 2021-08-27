package com.imangazalievm.bubbble.di.shotssearch

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.shotssearch.ShotsSearchPresenterModule
import com.imangazalievm.bubbble.presentation.mvp.shotslist.ShotsPresenter
import com.imangazalievm.bubbble.presentation.mvp.shotssearch.ShotsSearchPresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ShotsSearchPresenterModule::class]
)
interface ShotsSearchPresenterComponent {

    fun getPresenter(): ShotsSearchPresenter

}