package com.imangazalievm.bubbble.presentation.utils;

import android.widget.Toast;

import com.imangazalievm.bubbble.BubbbleApplication;
import com.imangazalievm.bubbble.BuildConfig;

public class DebugUtils {

    public static void showDebugErrorMessage(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
            Toast.makeText(BubbbleApplication.getInstance(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
