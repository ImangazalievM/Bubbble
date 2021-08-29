package com.imangazalievm.bubbble.di.shotslist

import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.presentation.shotslist.ShotsPresenter
import dagger.Component

@Presenter
@Component(dependencies = [ApplicationComponent::class], modules = [ShotsPresenterModule::class])
interface ShotsPresenterComponent {

    fun getPresenter(): ShotsPresenter

}