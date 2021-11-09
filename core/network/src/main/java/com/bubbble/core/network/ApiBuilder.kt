package com.bubbble.core.network

import android.content.Context
import com.bubbble.core.network.di.ApiInterceptors
import com.bubbble.core.network.di.ApiNetworkInterceptors
import com.bubbble.core.network.di.BaseApiUrl
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.reflect.KClass

class ApiBuilder @Inject constructor(
    private val context: Context,
    private val okHttpProvider: OkHttpProvider,
    private val gsonProvider: GsonProvider,
    @BaseApiUrl private val baseUrl: String,
    @ApiInterceptors private val interceptors: List<Interceptor>,
    @ApiNetworkInterceptors private val networkInterceptors: List<Interceptor>
) {

    fun <T : Any> createApi(apiClass: KClass<T>): T {
        val client = okHttpProvider.create(getInterceptors(), networkInterceptors).build()
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonProvider.create()))
                .client(client)
                .baseUrl(baseUrl)
                .build()
        return retrofit.create(apiClass.java)
    }

    private fun getInterceptors(): List<Interceptor> = listOf(
            *interceptors.toTypedArray()
    )

}