package com.bubbble.di.injector

interface ComponentFactory<T : ComponentApi> {
    fun create(componentManager: ComponentManager): T
}