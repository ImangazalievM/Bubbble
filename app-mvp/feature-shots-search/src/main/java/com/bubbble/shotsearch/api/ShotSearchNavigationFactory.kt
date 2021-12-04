package com.bubbble.shotsearch.api

import com.github.terrakok.cicerone.Screen

interface ShotSearchNavigationFactory {

    fun shotDetailsScreen(shotSlug: String): Screen

}