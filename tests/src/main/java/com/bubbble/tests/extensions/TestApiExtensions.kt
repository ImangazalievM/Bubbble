package com.bubbble.tests.extensions

import com.bubbble.core.network.GsonProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

fun <T : Any> createApi(apiClass: KClass<T>): T {
    //val client = okHttpProvider.create(interceptors, getNetworkInterceptors()).build()
    val gsonProvider = GsonProvider()
    val host = "http://localhost:9999/"
    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonProvider.create()))
            .client(OkHttpClient())
            .baseUrl(host)
            .build()

    return retrofit.create(apiClass.java)
}