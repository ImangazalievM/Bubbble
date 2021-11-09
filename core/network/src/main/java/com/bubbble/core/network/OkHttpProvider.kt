package com.bubbble.core.network

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OkHttpProvider @Inject constructor(

) {

    fun create(
            interceptors: List<Interceptor>,
            networkInterceptors: List<Interceptor>
    ): OkHttpClient.Builder {
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build()
        return OkHttpClient.Builder().apply {
            connectionSpecs(listOf(spec, ConnectionSpec.CLEARTEXT))
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
            writeTimeout(120, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)

            for (interceptor in interceptors) {
                addInterceptor(interceptor)
            }

            for (networkInterceptor in networkInterceptors) {
                addNetworkInterceptor(networkInterceptor)
            }
        }
    }

}