package com.bubbble.di.injector

import kotlin.reflect.KClass

object ComponentManager {

    private val holderStorage = mutableMapOf<String, ComponentHolder<out ComponentApi>>()
    private val factoryStorage = mutableMapOf<String, ComponentFactory<out ComponentApi>>()

    fun <T : ComponentApi> registerFactory(factory: ComponentFactory<T>, klass: KClass<T>) {
        factoryStorage[klass.qualifiedName!!] = factory
    }

    inline fun <reified T : ComponentApi> getOrCreateComponent(): T {
        return getOrCreateComponent(T::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ComponentApi> getOrCreateComponent(klass: KClass<T>): T {
        return getComponentHolder(klass).getOrCreate()
    }

    inline fun <reified T : ComponentApi> getComponent(): T {
        return getComponent(T::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ComponentApi> getComponent(klass: KClass<T>): T {
        if (!factoryStorage.contains(klass.qualifiedName!!))
            throw IllegalStateException("Factory for ${klass.qualifiedName!!} not registered")
        return getComponentHolder(klass).getWithoutRef()
    }

    fun <T : ComponentApi> releaseComponent(klass: KClass<T>) {
        getComponentHolder(klass).release()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ComponentApi> getComponentHolder(klass: KClass<T>): ComponentHolder<T> {
        val key = klass.qualifiedName!!
        return holderStorage.getOrElse(key) {
            val componentHolder = ComponentHolder(getFactoryForClass(klass), this)
            holderStorage[key] = componentHolder
            return@getOrElse componentHolder
        } as ComponentHolder<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ComponentApi> getFactoryForClass(klass: KClass<T>): ComponentFactory<T> {
        if (!factoryStorage.contains(klass.qualifiedName!!))
            throw IllegalStateException("Factory for ${klass.qualifiedName!!} not registered")
        return factoryStorage[klass.qualifiedName!!] as ComponentFactory<T>
    }

}