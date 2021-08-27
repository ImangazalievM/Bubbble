package com.imangazalievm.bubbble.di.userprofile

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.userprofile.UserFollowersPresenterModule
import com.imangazalievm.bubbble.presentation.mvp.userprofile.UserFollowersPresenter
import dagger.Component

@Presenter
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [UserFollowersPresenterModule::class]
)
interface UserFollowersPresenterComponent {

    fun getPresenter(): UserFollowersPresenter

}