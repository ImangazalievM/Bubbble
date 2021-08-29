package com.imangazalievm.bubbble.di.shotdetails

import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsPresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ShotDetailsPresenterModule::class]
)
interface ShotDetailsPresenterComponent {

    fun getPresenter(): ShotDetailsPresenter

}