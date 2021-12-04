package com.bubbble.shotsearch.api

import android.content.Context
import android.content.Intent
import com.bubbble.shotsearch.ShotsSearchActivity
import com.bubbble.coreui.navigationargs.buildIntent
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