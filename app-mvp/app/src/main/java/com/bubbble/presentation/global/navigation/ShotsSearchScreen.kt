package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.shotssearch.ShotsSearchActivity
import com.bubbble.ui.navigationargs.buildIntent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import java.io.Serializable

class ShotsSearchScreen(
    private val query: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return buildIntent<ShotsSearchActivity>(context, Data(query))
    }

    class Data(val query: String) : Serializable

}