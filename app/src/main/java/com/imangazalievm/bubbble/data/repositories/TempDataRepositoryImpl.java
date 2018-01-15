package com.imangazalievm.bubbble.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.imangazalievm.bubbble.BuildConfig;
import com.imangazalievm.bubbble.domain.global.repositories.TempDataRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TempDataRepositoryImpl implements TempDataRepository {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";
    private static final String PREF_API_TOKEN = "api_token";

    private SharedPreferences prefs;

    @Inject
    public TempDataRepositoryImpl(Context context) {
        this.prefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Completable saveToken(String token) {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, token).apply());
    }

    @Override
    public Single<String> getToken() {
        return Single.fromCallable(() -> prefs.getString(PREF_API_TOKEN, BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN));
    }

    @Override
    public Completable clearToken() {
        return Completable.fromAction(() -> prefs.edit().putString(PREF_API_TOKEN, null).apply());

    }

}
