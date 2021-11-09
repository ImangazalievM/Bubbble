package com.bubbble

import android.app.Application
import com.bubbble.core.AppContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BubbbleApplication : Application() {

    companion object {
        lateinit var instance: BubbbleApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()

        AppContext.setContext(this)

        instance = this
    }

}