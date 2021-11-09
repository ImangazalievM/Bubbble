package com.bubbble.di.modules

import com.bubbble.di.qualifiers.OkHttpInterceptors
import okhttp3.logging.HttpLoggingInterceptor
import com.bubbble.di.qualifiers.OkHttpNetworkInterceptors
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(): List<Interceptor> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return listOf(httpLoggingInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return listOf(StethoInterceptor())
    }

}