package com.bubbble.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AppContext {
    lateinit var instance: Context
        private set

    fun setContext(app: Application) {
        instance = app
    }

}