package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;

import dagger.Module;

@Module
public class PresentationModule {

    private PermissionsManager permissionsManager;

    public PresentationModule(PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

}
