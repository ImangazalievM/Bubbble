package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.di.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotsPresenterModule {

    private final String sortType;

    public ShotsPresenterModule(String sortType) {
        this.sortType = sortType;
    }

    @Provides
    @Presenter
    String provideSortType() {
        return sortType;
    }

}
