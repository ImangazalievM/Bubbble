package com.imangazalievm.bubbble.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }

}
