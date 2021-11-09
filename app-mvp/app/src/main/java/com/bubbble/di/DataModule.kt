package com.bubbble.di

import android.util.Log
import com.bubbble.core.network.Dribbble
import com.bubbble.core.network.DribbbleApi
import com.bubbble.core.network.NetworkChecker
import com.bubbble.core.network.di.BaseApiUrl
import com.bubbble.core.network.interceptors.NetworkCheckInterceptor
import com.bubbble.data.di.DribbbleWebSite
import com.bubbble.data.global.prefs.TempPreferences
import com.bubbble.di.qualifiers.OkHttpInterceptors
import com.bubbble.di.qualifiers.OkHttpNetworkInterceptors
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    @DribbbleWebSite
    fun dribbbleUrl(): String = Dribbble.URL

    @Provides
    @Singleton
    @BaseApiUrl
    fun dribbbleApiUrl(): String = Dribbble.URL

    @Provides
    @Singleton
    fun tempPreferences(tempPreferences: TempPreferences): TempPreferences {
        return tempPreferences
    }

    @Provides
    @Singleton
    fun okHttpClient(
        networkChecker: NetworkChecker,
        @OkHttpInterceptors interceptors: List<@JvmSuppressWildcards Interceptor>,
        @OkHttpNetworkInterceptors networkInterceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(
            NetworkCheckInterceptor(
                networkChecker
            )
        )
        //okHttpBuilder.addInterceptor(
        //    DribbbleTokenInterceptor(
        //        BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN
        //    )
        //)
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
    fun retrofit(
        okHttpClient: OkHttpClient,
        @BaseApiUrl
        baseUrl: String
    ): Retrofit {
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
    fun dribbbleApi(retrofit: Retrofit): DribbbleApi {
        return retrofit.create(DribbbleApi::class.java)
    }

}