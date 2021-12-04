package com.bubbble.userprofile.api

import com.github.terrakok.cicerone.Screen

interface UserProfileNavigationFactory {

    fun shotDetailsScreen(shotSlug: String): Screen

}