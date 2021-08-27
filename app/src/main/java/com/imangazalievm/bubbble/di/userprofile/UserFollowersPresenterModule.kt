package com.imangazalievm.bubbble.di.userprofile

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import dagger.Module
import dagger.Provides

@Module
class UserFollowersPresenterModule(private val userId: Long) {

    @Provides
    @Presenter
    fun provideUserId(): Long = userId

}