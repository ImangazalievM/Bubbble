package com.bubbble.coreui.di

import com.bubbble.core.di.CoreComponent
import com.bubbble.coreui.mvp.ErrorHandler
import com.bubbble.coreui.permissions.PermissionsManager
import com.bubbble.coreui.resourcesmanager.ResourcesManager
import com.bubbble.di.injector.ComponentApi
import com.bubbble.di.injector.ComponentFactory
import com.bubbble.di.injector.ComponentManager
import dagger.Component

@CoreUiScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [CoreUiModule::class]
)
interface CoreUiComponent : ComponentApi {

    val errorHandler: ErrorHandler
    val resourcesManager: ResourcesManager
    val permissionsManager: PermissionsManager

    object Factory : ComponentFactory<CoreUiComponent> {
        override fun create(manager: ComponentManager): CoreUiComponent {
            return DaggerCoreUiComponent.builder()
                .coreComponent(manager.getComponent())
                .coreUiModule(CoreUiModule())
                .build()
        }
    }

}

val coreUiComponent: CoreUiComponent
    get() = ComponentManager.getOrCreateComponent()