package com.bubbble.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject
import kotlin.reflect.KClass

open class GsonProvider @Inject constructor() {

    fun create(): Gson {
        val typeAdapters = listOf(
                *getTypeAdapters().toTypedArray()
        )
        return GsonBuilder()
                .also { build ->
                    typeAdapters.forEach { adapter ->
                        build.registerTypeAdapter(adapter.first.java, adapter.second)
                    }
                }
                .create()
    }

    open fun getTypeAdapters(): List<Pair<KClass<*>, Any>> {
        return emptyList()
    }

}