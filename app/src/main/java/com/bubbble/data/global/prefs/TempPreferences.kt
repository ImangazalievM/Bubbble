package com.bubbble.data.global.prefs

import android.content.Context
import android.content.SharedPreferences
import com.bubbble.BuildConfig
import javax.inject.Inject

class TempPreferences @Inject constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String?) {
        prefs.edit().putString(PREF_API_TOKEN, token).apply()
    }

    val token: String
        get() = prefs.getString(
            PREF_API_TOKEN,
            BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN
        )!!

    fun clearToken() {
        prefs.edit().putString(PREF_API_TOKEN, null).apply()
    }

    companion object {
        private const val APP_PREFS_FILE_NAME = "app_preferences"
        private const val PREF_API_TOKEN = "api_token"
    }

}