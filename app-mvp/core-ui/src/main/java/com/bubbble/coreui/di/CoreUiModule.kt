package com.bubbble.coreui.di

import com.bubbble.coreui.mvp.ErrorHandler
import com.bubbble.coreui.permissions.AndroidPermissionsManager
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.coreui.resourcesmanager.AndroidResourcesManager
import com.bubbble.coreui.resourcesmanager.ResourcesManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [CoreUiModule.Bindings::class])
class CoreUiModule {

    @Provides
    @CoreUiScope
    fun provideErrorHandler(resourcesManager: ResourcesManager): ErrorHandler {
        return ErrorHandler(resourcesManager)
    }

    @Module
    interface Bindings {

        @Binds
        @CoreUiScope
        fun resourcesManager(manager: AndroidResourcesManager): ResourcesManager

        @Binds
        @CoreUiScope
        fun permissionsManager(manager: AndroidPermissionsManager): PermissionsManager

    }

}