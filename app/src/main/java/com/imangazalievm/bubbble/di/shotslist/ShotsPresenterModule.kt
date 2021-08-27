package com.imangazalievm.bubbble.di.shotslist;

import com.imangazalievm.bubbble.di.global.scopes.Presenter;

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
