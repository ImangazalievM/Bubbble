package com.bubbble.shotdetails.api

import android.content.Context
import android.content.Intent
import com.bubbble.shotdetails.shotzoom.ShotZoomActivity
import com.bubbble.coreui.navigationargs.buildIntent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import java.io.Serializable

class ShotImageZoomScreen(
    private val title: String,
    private val shotUrl: String,
    private val imageUrl: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return buildIntent<ShotZoomActivity>(
            context, Data(
                title = title,
                shotUrl = shotUrl,
                imageUrl = imageUrl
            )
        )
    }

    class Data(
        val title: String,
        val shotUrl: String,
        val imageUrl: String
    ) : Serializable

}