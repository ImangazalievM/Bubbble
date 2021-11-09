package com.bubbble.shots.api

import android.content.Context
import android.content.Intent

interface ShotsNavigationFactory {

    fun shotsSearchScreen(context: Context, query: String): Intent

}