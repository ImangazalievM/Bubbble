package com.imangazalievm.bubbble;

import android.app.Application;
import android.content.Context;

import com.imangazalievm.bubbble.data.network.DribbbleApiConstants;
import com.imangazalievm.bubbble.di.ApplicationComponent;
import com.imangazalievm.bubbble.di.DaggerApplicationComponent;
import com.imangazalievm.bubbble.di.modules.ApplicationModule;
import com.imangazalievm.bubbble.di.modules.DataModule;

public class BubbbleApplication extends Application {

    private static ApplicationComponent applicationComponent;
    private static BubbbleApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        applicationComponent = buildComponent();
    }

    public ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule(DribbbleApiConstants.BASE_URL))
                .build();
    }

    public static Context getInstance() {
        return instance;
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

}
