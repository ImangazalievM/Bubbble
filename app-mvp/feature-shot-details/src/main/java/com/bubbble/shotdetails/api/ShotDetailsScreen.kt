package com.bubbble.shotdetails.api

import android.content.Context
import android.content.Intent
import com.bubbble.shotdetails.ShotDetailsActivity
import com.bubbble.ui.navigationargs.buildIntent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import java.io.Serializable

class ShotDetailsScreen(
    private val shotSlug: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return buildIntent<ShotDetailsActivity>(context, Data(shotSlug))
    }

    class Data(val shotSlug: String) : Serializable

}