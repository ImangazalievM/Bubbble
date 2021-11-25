package com.bubbble.presentation.global.navigation

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.userprofile.UserProfileActivity
import com.bubbble.ui.navigationargs.buildIntent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import java.io.Serializable

class UserProfileScreen(
    private val userName: String
) : ActivityScreen {

    override fun createIntent(context: Context): Intent {
        return buildIntent<UserProfileActivity>(context, Data(userName))
    }

    class Data(val userName: String) : Serializable

}