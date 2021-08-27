package com.imangazalievm.bubbble.di.userprofile

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.userprofile.UserProfilePresenterModule
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserProfilePresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [UserProfilePresenterModule::class]
)
interface UserProfilePresenterComponent {

    fun getPresenter(): UserProfilePresenter

}