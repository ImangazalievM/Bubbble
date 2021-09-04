package com.bubbble.di

import android.content.Context
import com.bubbble.coreui.mvp.ErrorHandler
import com.bubbble.coreui.permissions.AndroidPermissionsManager
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.coreui.resourcesmanager.AndroidResourcesManager
import com.bubbble.coreui.resourcesmanager.ResourcesManager
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
    fun provideErrorHandler(resourcesManager: ResourcesManager): ErrorHandler {
        return ErrorHandler(resourcesManager)
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