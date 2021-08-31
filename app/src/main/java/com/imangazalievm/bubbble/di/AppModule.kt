package com.imangazalievm.bubbble.di

import android.content.Context
import com.google.gson.Gson
import com.imangazalievm.bubbble.data.global.network.ErrorHandler
import com.imangazalievm.bubbble.presentation.global.permissions.AndroidPermissionsManager
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager
import com.imangazalievm.bubbble.presentation.global.resourcesmanager.AndroidResourcesManager
import com.imangazalievm.bubbble.presentation.global.resourcesmanager.ResourcesManager
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
    fun provideErrorHandler(gson: Gson): ErrorHandler {
        return ErrorHandler(gson)
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