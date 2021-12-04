package com.bubbble.userprofile.api

import android.content.Context
import android.content.Intent
import com.bubbble.userprofile.UserProfileActivity
import com.bubbble.coreui.navigationargs.buildIntent
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