package com.imangazalievm.bubbble.di.shotdetails;

import com.imangazalievm.bubbble.di.global.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotDetailsPresenterModule {

    private final long shotId;

    public ShotDetailsPresenterModule(long shotId) {
        this.shotId = shotId;
    }

    @Provides
    @Presenter
    long provideShotId() {
        return shotId;
    }

}
