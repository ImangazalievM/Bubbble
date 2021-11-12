package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.github.terrakok.cicerone.androidx.ActivityScreen

class AppSettingsScreen : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        return intent
    }

}