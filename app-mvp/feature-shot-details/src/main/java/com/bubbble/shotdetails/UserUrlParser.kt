package com.bubbble.shotdetails

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Inject

class UserUrlParser @Inject constructor() {

    fun getUserName(url: String): String? {
        return url.toHttpUrlOrNull()
            ?.pathSegments
            ?.get(0)
    }

}


