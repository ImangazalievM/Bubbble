package com.bubbble.shotdetails

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

class UserUrlParser @Inject constructor() {

    fun parse(url: String): Long? {
        return url.toHttpUrlOrNull()
            ?.pathSegments?.get(0)
            ?.toLong()
    }

}


