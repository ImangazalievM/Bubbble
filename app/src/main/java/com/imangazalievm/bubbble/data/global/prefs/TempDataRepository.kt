package com.imangazalievm.bubbble.data.global.prefs

import android.content.Context
import android.content.SharedPreferences
import com.imangazalievm.bubbble.BuildConfig
import io.reactivex.Completable
import com.imangazalievm.bubbble.data.global.prefs.TempDataRepository
import io.reactivex.Single
import javax.inject.Inject

class TempDataRepository @Inject constructor(context: Context) {
    private val prefs: SharedPreferences
    fun saveToken(token: String?): Completable {
        return Completable.fromAction { prefs.edit().putString(PREF_API_TOKEN, token).apply() }
    }

    val token: Single<String?>
        get() = Single.fromCallable {
            prefs.getString(
                PREF_API_TOKEN,
                BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN
            )
        }

    fun clearToken(): Completable {
        return Completable.fromAction { prefs.edit().putString(PREF_API_TOKEN, null).apply() }
    }

    companion object {
        private const val APP_PREFS_FILE_NAME = "app_preferences"
        private const val PREF_API_TOKEN = "api_token"
    }

    init {
        prefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}