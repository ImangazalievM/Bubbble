package com.imangazalievm.bubbble.test;

import android.os.Build;
import android.support.annotation.NonNull;

import com.imangazalievm.bubbble.BubbbleTestApplication;
import com.imangazalievm.bubbble.BuildConfig;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

public class BubbbleTestRunner extends RobolectricTestRunner {

    private static final int SDK_EMULATE_LEVEL = Build.VERSION_CODES.M;

    public BubbbleTestRunner(@NonNull Class<?> clazz) throws Exception {
        super(clazz);
    }

    @Override
    public Config getConfig(@NonNull Method method) {
        final Config defaultConfig = super.getConfig(method);
        return new Config.Implementation(
                new int[]{SDK_EMULATE_LEVEL},
                defaultConfig.manifest(),
                defaultConfig.qualifiers(),
                defaultConfig.packageName(),
                defaultConfig.abiSplit(),
                defaultConfig.resourceDir(),
                defaultConfig.assetDir(),
                defaultConfig.buildDir(),
                defaultConfig.shadows(),
                defaultConfig.instrumentedPackages(),
                BubbbleTestApplication.class, // Notice that we override real application class for Unit tests.
                defaultConfig.libraries(),
                defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
        );
    }

    @NonNull
    public static BubbbleTestApplication bubbbleTestApplication() {
        return (BubbbleTestApplication) RuntimeEnvironment.application;
    }

}