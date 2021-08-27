package com.imangazalievm.bubbble.di.shotdetails

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import dagger.Module
import dagger.Provides

@Module
class ShotDetailsPresenterModule(private val shotId: Long) {

    @Provides
    @Presenter
    fun provideShotId(): Long = shotId

}