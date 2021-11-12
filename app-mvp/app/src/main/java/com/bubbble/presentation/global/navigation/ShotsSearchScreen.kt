package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.shotssearch.ShotsSearchActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen

class ShotsSearchScreen(
    private val query: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return ShotsSearchActivity.buildIntent(context, query)
    }

}