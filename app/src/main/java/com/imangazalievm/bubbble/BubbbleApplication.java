package com.imangazalievm.bubbble;

import android.app.Application;
import android.content.Context;

import com.imangazalievm.bubbble.data.network.DribbbleApiConstants;
import com.imangazalievm.bubbble.di.ApplicationComponent;
import com.imangazalievm.bubbble.di.DaggerApplicationComponent;
import com.imangazalievm.bubbble.di.modules.ApplicationModule;
import com.imangazalievm.bubbble.di.modules.DataModule;

public class BubbbleApplication extends Application {

    private static ApplicationComponent mApplicationComponent;
    private static BubbbleApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mApplicationComponent = getComponent();
    }

    public static Context getInstance() {
        return sInstance;
    }

    public static ApplicationComponent component() {
        return mApplicationComponent;
    }

    public ApplicationComponent getComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule(DribbbleApiConstants.BASE_URL))
                .build();
    }

}
