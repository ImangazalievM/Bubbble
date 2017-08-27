package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotZoomPresenterModule {

    private PermissionsManager permissionsManager;
    private final String shotTitle;
    private final String shotUrl;
    private final String imageUrl;

    public ShotZoomPresenterModule(PermissionsManager permissionsManager, String shotTitle, String shotUrl, String imageUrl) {
        this.permissionsManager = permissionsManager;
        this.shotTitle = shotTitle;
        this.shotUrl = shotUrl;
        this.imageUrl = imageUrl;
    }

    @Provides
    @Presenter
    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
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
