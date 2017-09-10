package com.imangazalievm.bubbble.di.modules;

import com.imangazalievm.bubbble.di.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShotsSearchPresenterModule {

    private final String searchQuery;

    public ShotsSearchPresenterModule(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Provides
    @Presenter
    String provideSearchQuery() {
        return searchQuery;
    }

}
