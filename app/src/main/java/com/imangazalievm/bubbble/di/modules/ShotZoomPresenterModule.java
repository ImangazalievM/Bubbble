package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.di.scopes.Presenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotZoomPresenterModule {

    private final String shotTitle;
    private final String shotUrl;
    private final String imageUrl;

    public ShotZoomPresenterModule(String shotTitle, String shotUrl, String imageUrl) {
        this.shotTitle = shotTitle;
        this.shotUrl = shotUrl;
        this.imageUrl = imageUrl;
    }

    @Provides
    @Presenter
    @Named("shot_title")
    public String getShotTitle() {
        return shotTitle;
    }

    @Provides
    @Presenter
    @Named("shot_url")
    public String getShotUrl() {
        return shotUrl;
    }

    @Provides
    @Presenter
    @Named("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

}
