package com.bubbble

import android.app.Application
import com.bubbble.core.network.ApiConstants
import com.bubbble.di.AppModule
import com.bubbble.di.ApplicationComponent
import com.bubbble.di.DaggerApplicationComponent
import com.bubbble.di.DataModule

open class BubbbleApplication : Application() {


    companion object {
        lateinit var component: ApplicationComponent
            private set
        lateinit var instance: BubbbleApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = buildComponent()
    }

    open fun buildComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule(ApiConstants.DRIBBBLE_API_URL))
            .build()
    }

}