package com.bubbble.di

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.global.navigation.ShotsSearchScreen
import com.bubbble.presentation.shotssearch.ShotsSearchActivity
import com.bubbble.shots.api.ShotsNavigationFactory
import com.github.terrakok.cicerone.Screen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun applicationContext(
        @ApplicationContext context: Context
    ): Context = context

}