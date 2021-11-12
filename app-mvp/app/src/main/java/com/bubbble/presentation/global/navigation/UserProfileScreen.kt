package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.userprofile.UserProfileActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen

class UserProfileScreen(
    private val userName: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return UserProfileActivity.buildIntent(context, userName)
    }

}