package com.bubbble.di

import com.bubbble.core.models.shot.Shot
import com.bubbble.presentation.global.navigation.*
import com.bubbble.shotdetails.api.ShotDetailsNavigationFactory
import com.bubbble.shots.api.ShotsNavigationFactory
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    @Singleton
    fun cicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun navigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun navigatorRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun shotsNavigationFactory() = object : ShotsNavigationFactory {

        override fun shotDetailsScreen(shotId: Long): Screen = ShotDetailsScreen(shotId)

        override fun shotsSearchScreen(query: String) = ShotsSearchScreen(query)
    }

    @Provides
    @Singleton
    fun shotDetailsNavigationFactory() = object : ShotDetailsNavigationFactory {

        override fun userProfileScreen(userName: String) = UserProfileScreen(userName)

        override fun shotImageScreen(shot: Shot) = ShotImageZoomScreen(
            title = shot.title,
            shotUrl = shot.shotUrl,
            imageUrl = shot.imageUrl
        )

        override fun appSettingsScreen() = AppSettingsScreen()
    }

}