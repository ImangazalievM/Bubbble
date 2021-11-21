package com.bubbble.shotdetails.api

import android.content.Context
import android.content.Intent
import com.bubbble.shotdetails.ShotDetailsActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen

class ShotDetailsScreen(
    private val shotSlug: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return ShotDetailsActivity.buildIntent(context, shotSlug)
    }
}