package com.bubbble.data.global.parsing

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class PageDownloader @Inject constructor(
    private val okHttpClient: OkHttpClient,
) {

    fun download(url: HttpUrl): String {
        val request: Request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()
        if (response.code == 200) {
            return response.body!!.string()
        } else {
            throw PageDownloadException(
                message = "An error caused when downloading page",
                httpCode = response.code,
                response = response.body?.string()
            )
        }
    }

}