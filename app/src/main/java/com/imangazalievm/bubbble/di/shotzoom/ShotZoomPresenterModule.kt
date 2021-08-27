package com.imangazalievm.bubbble.di.shotzoom

import com.imangazalievm.bubbble.di.global.scopes.Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ShotZoomPresenterModule(
    private val shotTitle: String,
    private val shotUrl: String,
    private val imageUrl: String
) {

    @Provides
    @Presenter
    @Named("shot_title")
    fun getShotTitle(): String {
        return shotTitle;
    }

    @Provides
    @Presenter
    @Named("shot_url")
    fun getShotUrl(): String {
        return shotUrl;
    }

    @Provides
    @Presenter
    @Named("image_url")
    fun getImageUrl(): String {
        return imageUrl;
    }

}