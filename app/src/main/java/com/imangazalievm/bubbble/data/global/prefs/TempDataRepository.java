package com.imangazalievm.bubbble.data.global.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.imangazalievm.bubbble.BuildConfig;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TempDataRepository {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";
    private static final String PREF_API_TOKEN = "api_token";

    private SharedPreferences prefs;

    @Inject
    public TempDataRepository(Context context) {
        this.prefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public Completable saveToken(String token) {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, token).apply());
    }

    public Single<String> getToken() {
        return Single.fromCallable(() -> prefs.getString(PREF_API_TOKEN, BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN));
    }

    public Completable clearToken() {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, null).apply());

    }

}
