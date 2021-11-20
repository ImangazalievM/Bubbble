package com.bubbble.shotdetails.api

import android.content.Context
import android.content.Intent
import com.bubbble.shotdetails.shotzoom.ShotZoomActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen

class ShotImageZoomScreen(
    private val title: String,
    private val shotUrl: String,
    private val imageUrl: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return ShotZoomActivity.buildIntent(
            context = context,
            title = title,
            shotUrl = shotUrl,
            imageUrl = imageUrl
        )
    }

}