package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.di.scopes.Presenter;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotDetailsPresenterModule {

    private PermissionsManager permissionsManager;
    private final long shotId;

    public ShotDetailsPresenterModule(PermissionsManager permissionsManager, long shotId) {
        this.permissionsManager = permissionsManager;
        this.shotId = shotId;
    }

    @Provides
    @Presenter
    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    @Provides
    @Presenter
    long provideShotId() {
        return shotId;
    }

}
