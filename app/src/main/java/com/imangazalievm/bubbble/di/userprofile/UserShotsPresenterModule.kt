package com.imangazalievm.bubbble.di.userprofile;

import com.imangazalievm.bubbble.di.global.scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class UserShotsPresenterModule {

    private final long userId;

    public UserShotsPresenterModule(long userId) {
        this.userId = userId;
    }

    @Provides
    @Presenter
    long provideUserId() {
        return userId;
    }

}
