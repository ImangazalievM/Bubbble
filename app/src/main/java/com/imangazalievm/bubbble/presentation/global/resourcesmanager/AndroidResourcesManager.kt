package com.imangazalievm.bubbble.presentation.global.resourcesmanager

import android.content.Context
import javax.inject.Inject

class AndroidResourcesManager @Inject constructor(
        private val context: Context
) : ResourcesManager {

    override fun getString(resourceId: Int, vararg args: Any): String {
        return context.resources.getString(resourceId, *args)
    }

    override fun getInteger(resourceId: Int): Int {
        return context.resources.getInteger(resourceId)
    }

}