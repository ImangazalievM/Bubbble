package com.bubbble.coreui.di

import com.bubbble.coreui.mvp.ErrorHandler
import com.bubbble.coreui.permissions.AndroidPermissionsManager
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.coreui.resourcesmanager.AndroidResourcesManager
import com.bubbble.coreui.resourcesmanager.ResourcesManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreUiModule {

    @Provides
    @Singleton
    fun provideErrorHandler(resourcesManager: ResourcesManager): ErrorHandler {
        return ErrorHandler(resourcesManager)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        @Singleton
        fun resourcesManager(manager: AndroidResourcesManager): ResourcesManager

        @Binds
        @Singleton
        fun permissionsManager(manager: AndroidPermissionsManager): PermissionsManager

    }

}