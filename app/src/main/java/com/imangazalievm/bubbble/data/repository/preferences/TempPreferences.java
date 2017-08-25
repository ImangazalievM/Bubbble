package com.imangazalievm.bubbble.data.repository.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.imangazalievm.bubbble.BuildConfig;

import javax.inject.Inject;

public class TempPreferences {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";

    private static final String PREF_API_TOKEN = "api_token";

    private SharedPreferences mPrefs;

    @Inject
    public TempPreferences(Context context) {
        this.mPrefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void saveToke(String token) {
        mPrefs.edit().putString(PREF_API_TOKEN, token).apply();
    }

    @NonNull
    public String getToken() {
        return mPrefs.getString(PREF_API_TOKEN, BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN);
    }

}
