package com.bubbble.di

import android.content.Context
import com.google.gson.Gson
import com.bubbble.core.ErrorHandler
import com.bubbble.presentation.global.permissions.AndroidPermissionsManager
import com.bubbble.presentation.global.permissions.PermissionsManager
import com.bubbble.presentation.global.resourcesmanager.AndroidResourcesManager
import com.bubbble.presentation.global.resourcesmanager.ResourcesManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.Bindings::class])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun applicationContext(): Context = context

    @Provides
    @Singleton
    fun provideErrorHandler(gson: Gson): com.bubbble.core.ErrorHandler {
        return com.bubbble.core.ErrorHandler(gson)
    }

    @Module
    interface Bindings {

        @Binds
        @Singleton
        fun resourcesManager(manager: AndroidResourcesManager): ResourcesManager

        @Binds
        @Singleton
        fun permissionsManager(manager: AndroidPermissionsManager): PermissionsManager

    }

}