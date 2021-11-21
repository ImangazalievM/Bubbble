package com.bubbble.shotdetails.api

import com.github.terrakok.cicerone.Screen

interface ShotDetailsNavigationFactory {

    fun userProfileScreen(userName: String): Screen

    fun shotImageScreen(
        title: String,
        shotUrl: String,
        imageUrl: String
    ): Screen

    fun appSettingsScreen(): Screen

}