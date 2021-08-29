package com.imangazalievm.bubbble.di.global

import android.content.Context
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
    fun provideApplicationContext(): Context = context

    @Provides
    @Singleton
    fun provideErrorHandler(handler: ErrorHandler): ErrorHandler = handler

    @Module
    interface Bindings {

        @Binds
        @Singleton
        fun provideResourcesManager(manager: AndroidResourcesManager): ResourcesManager

        @Binds
        @Singleton
        fun providePermissionsManager(manager: AndroidPermissionsManager): PermissionsManager

    }

}