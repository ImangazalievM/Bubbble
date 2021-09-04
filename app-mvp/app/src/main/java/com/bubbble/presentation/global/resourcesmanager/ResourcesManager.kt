package com.bubbble.presentation.global.resourcesmanager

interface ResourcesManager {

    fun getString(resourceId: Int, vararg args: Any): String

    fun getInteger(resourceId: Int): Int

}