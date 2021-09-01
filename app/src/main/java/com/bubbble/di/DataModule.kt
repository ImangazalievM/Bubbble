package com.bubbble.di

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.bubbble.BuildConfig
import com.bubbble.core.Dribbble
import com.bubbble.core.DribbbleApi
import com.bubbble.core.NetworkChecker
import com.bubbble.core.interceptors.DribbbleTokenInterceptor
import com.bubbble.core.interceptors.NetworkCheckInterceptor
import com.bubbble.data.global.prefs.TempPreferences
import com.bubbble.di.qualifiers.DribbbleWebSite
import com.bubbble.di.qualifiers.OkHttpInterceptors
import com.bubbble.di.qualifiers.OkHttpNetworkInterceptors
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule(private val baseUrl: String) {

    @Provides
    @Singleton
    @DribbbleWebSite
    fun dribbbleUrl(): String = com.bubbble.core.Dribbble.URL

    @Provides
    @Singleton
    fun tempPreferences(tempPreferences: TempPreferences): TempPreferences {
        return tempPreferences
    }

    @Provides
    @Singleton
    fun okHttpClient(
        networkChecker: com.bubbble.core.NetworkChecker,
        @OkHttpInterceptors interceptors: List<@JvmSuppressWildcards Interceptor>,
        @OkHttpNetworkInterceptors networkInterceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(
            com.bubbble.core.interceptors.NetworkCheckInterceptor(
                networkChecker
            )
        )
        okHttpBuilder.addInterceptor(
            com.bubbble.core.interceptors.DribbbleTokenInterceptor(
                BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN
            )
        )
        for (interceptor in interceptors) {
            okHttpBuilder.addInterceptor(interceptor)
        }
        for (networkInterceptor in networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor)
        }

        val curlInterceptor = CurlInterceptor { curl -> Log.d("okhttp.OkHttpClient", curl) }
        okHttpBuilder.addNetworkInterceptor(curlInterceptor)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun dribbbleApi(retrofit: Retrofit): com.bubbble.core.DribbbleApi {
        return retrofit.create(com.bubbble.core.DribbbleApi::class.java)
    }

}