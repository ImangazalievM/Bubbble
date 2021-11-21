package com.bubbble.shots.api

import com.github.terrakok.cicerone.Screen

interface ShotsNavigationFactory {

    fun shotDetailsScreen(shotSlug: String): Screen

    fun shotsSearchScreen(query: String): Screen

}