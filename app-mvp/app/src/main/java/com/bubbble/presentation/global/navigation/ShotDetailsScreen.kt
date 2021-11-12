package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.shotssearch.ShotsSearchActivity
import com.bubbble.shotdetails.ShotDetailsActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen

class ShotDetailsScreen(
    private val shotId: Long
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return ShotDetailsActivity.buildIntent(context, shotId)
    }

}