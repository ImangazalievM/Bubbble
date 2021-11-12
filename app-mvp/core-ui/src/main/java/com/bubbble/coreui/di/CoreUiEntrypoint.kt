package com.bubbble.coreui.di

import com.bubbble.core.AppContext
import com.bubbble.coreui.mvp.ErrorHandler
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.coreui.resourcesmanager.ResourcesManager
import com.bubbble.di.injector.ComponentApi
import com.bubbble.di.injector.ComponentFactory
import com.bubbble.di.injector.ComponentManager
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.hilt.EntryPoint

import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreUiEntrypoint {

    val errorHandler: ErrorHandler
    val resourcesManager: ResourcesManager
    val permissionsManager: PermissionsManager
    val router: Router
    val navigatorHolder: NavigatorHolder

}

val coreUiEntrypoint: CoreUiEntrypoint
    get() = EntryPoints.get(AppContext.instance, CoreUiEntrypoint::class.java)