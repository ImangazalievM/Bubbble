package com.bubbble.data

import com.bubbble.core.network.di.BaseApiUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class TestModule(
    private val dribbbleUrl: String
) {

    @Provides
    @Singleton
    @BaseApiUrl
    fun dribbbleUrl(): String = dribbbleUrl

    @Provides
    @Singleton
    fun okHttpBuilder(): OkHttpClient = OkHttpClient.Builder().build()

}