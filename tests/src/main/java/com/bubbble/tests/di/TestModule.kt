package com.bubbble.tests.di

import com.bubbble.core.network.GsonProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideGson(provider: GsonProvider): Gson {
        return provider.create()
    }

}