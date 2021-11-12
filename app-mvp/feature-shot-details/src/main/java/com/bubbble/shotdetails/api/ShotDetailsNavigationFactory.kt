package com.bubbble.shotdetails.api

import com.bubbble.core.models.shot.Shot
import com.github.terrakok.cicerone.Screen

interface ShotDetailsNavigationFactory {

    fun userProfileScreen(userName: String): Screen

    fun shotImageScreen(shot: Shot): Screen

    fun appSettingsScreen(): Screen

}