package com.bubbble.core.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val application: Application) {

    @Provides
    @CoreScope
    fun provideContext(): Context = application

}