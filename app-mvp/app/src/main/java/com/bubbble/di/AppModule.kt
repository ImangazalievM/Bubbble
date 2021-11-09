package com.bubbble.di

import android.content.Context
import android.content.Intent
import com.bubbble.presentation.shotssearch.ShotsSearchActivity
import com.bubbble.shots.api.ShotsNavigationFactory
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

    @Provides
    @Singleton
    fun shotsNavigationFactory() = object : ShotsNavigationFactory {
        override fun shotsSearchScreen(context: Context, query: String): Intent {
            return ShotsSearchActivity.buildIntent(context, query)
        }
    }

}