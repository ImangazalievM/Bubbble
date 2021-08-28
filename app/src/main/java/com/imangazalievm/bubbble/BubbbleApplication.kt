package com.imangazalievm.bubbble

import android.app.Application
import com.imangazalievm.bubbble.di.global.ApplicationComponent
import com.imangazalievm.bubbble.di.global.DaggerApplicationComponent
import com.imangazalievm.bubbble.di.global.AppModule
import com.imangazalievm.bubbble.di.global.DataModule
import com.imangazalievm.bubbble.data.global.network.ApiConstants

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