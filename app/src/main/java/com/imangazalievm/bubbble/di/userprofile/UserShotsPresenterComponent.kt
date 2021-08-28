package com.imangazalievm.bubbble.di.userprofile

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.presentation.userprofile.shots.UserShotsPresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [UserShotsPresenterModule::class]
)
interface UserShotsPresenterComponent {

    fun getPresenter(): UserShotsPresenter

}