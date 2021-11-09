package com.bubbble.core.network.utils

import com.bubbble.core.network.Dribbble
import okhttp3.HttpUrl
import java.net.URL

fun urlBuilder(url: String): HttpUrl.Builder {
    val url = URL(Dribbble.Search.path)
    val builder = HttpUrl.Builder()
        .scheme(url.protocol)
        .host(url.host)
    url.path.split("/").forEach {
        builder.addPathSegment(it)
    }
    return builder
}