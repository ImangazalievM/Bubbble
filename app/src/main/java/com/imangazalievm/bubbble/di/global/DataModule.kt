package com.imangazalievm.bubbble.di.global

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imangazalievm.bubbble.BuildConfig
import com.imangazalievm.bubbble.data.global.network.*
import com.imangazalievm.bubbble.data.shots.CommentsRepository
import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.data.users.FollowersRepository
import com.imangazalievm.bubbble.data.shots.ImagesRepository
import com.imangazalievm.bubbble.data.global.prefs.TempPreferences
import okhttp3.OkHttpClient
import com.imangazalievm.bubbble.data.shots.DribbbleSearchDataSource
import com.imangazalievm.bubbble.di.global.qualifiers.OkHttpInterceptors
import com.imangazalievm.bubbble.di.global.qualifiers.OkHttpNetworkInterceptors
import com.imangazalievm.bubbble.data.global.network.interceptors.NetworkCheckInterceptor
import com.imangazalievm.bubbble.data.global.network.interceptors.DribbbleTokenInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
class DataModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideTempPreferences(tempPreferences: TempPreferences): TempPreferences {
        return tempPreferences
    }

    @Provides
    @Singleton
    fun provideDribbbleSearchDataSource(okHttpClient: OkHttpClient): DribbbleSearchDataSource {
        return DribbbleSearchDataSource(
            okHttpClient,
            Dribbble.URL
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkChecker: NetworkChecker,
        @OkHttpInterceptors interceptors: List<@JvmSuppressWildcards Interceptor>,
        @OkHttpNetworkInterceptors networkInterceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(NetworkCheckInterceptor(networkChecker))
        okHttpBuilder.addInterceptor(DribbbleTokenInterceptor(BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN))
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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideErrorHandler(gson: Gson): ErrorHandler {
        return ErrorHandler(gson)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): DribbbleApiService {
        return retrofit.create(DribbbleApiService::class.java)
    }
}