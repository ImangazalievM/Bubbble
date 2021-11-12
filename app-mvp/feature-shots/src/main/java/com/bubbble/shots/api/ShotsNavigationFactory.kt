package com.bubbble.shots.api

import com.github.terrakok.cicerone.Screen

interface ShotsNavigationFactory {

    fun shotDetailsScreen(shotId: Long): Screen

    fun shotsSearchScreen(query: String): Screen

}