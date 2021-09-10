package com.bubbble.di.injector

internal class ComponentHolder<T : ComponentApi>(
    private val componentFactory: ComponentFactory<T>,
    private val componentsManager: ComponentManager
) {

    private var component: T? = null

    @Synchronized
    fun getOrCreate(): T {
        if (component == null) {
            component = componentFactory.create(componentsManager)
        }
        return component!!
    }

    @Synchronized
    fun getWithoutRef(): T {
        if (component == null) {
            throw IllegalStateException("Component isn't created yet!")
        }
        return component!!
    }

    @Synchronized
    fun release() {
        component = null
    }

}