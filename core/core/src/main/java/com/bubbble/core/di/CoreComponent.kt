package com.bubbble.core.di

import android.app.Application
import android.content.Context
import com.bubbble.di.injector.ComponentApi
import com.bubbble.di.injector.ComponentFactory
import com.bubbble.di.injector.ComponentManager
import dagger.Component
import javax.inject.Singleton

@CoreScope
@Component(
    modules = [ContextModule::class]
)
interface CoreComponent : ComponentApi {

    val context: Context

    class Factory(
        private val application: Application
    ) : ComponentFactory<CoreComponent> {

        override fun create(manager: ComponentManager): CoreComponent {
            return DaggerCoreComponent.builder()
                .contextModule(ContextModule(application))
                .build()
        }

    }

}