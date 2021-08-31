package com.imangazalievm.bubbble

import android.app.Application
import com.imangazalievm.bubbble.data.global.network.ApiConstants
import com.imangazalievm.bubbble.di.AppModule
import com.imangazalievm.bubbble.di.ApplicationComponent
import com.imangazalievm.bubbble.di.DaggerApplicationComponent
import com.imangazalievm.bubbble.di.DataModule

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