package com.imangazalievm.bubbble.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.imangazalievm.bubbble.BuildConfig;
import com.imangazalievm.bubbble.domain.repository.ITempDataRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TempDataRepository implements ITempDataRepository {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";

    private static final String PREF_API_TOKEN = "api_token";

    private SharedPreferences prefs;

    @Inject
    public TempDataRepository(Context context) {
        this.prefs = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Completable saveToken(String token) {
        return Completable.create(e -> {
            prefs.edit().putString(PREF_API_TOKEN, token).apply();
            e.onComplete();
        });
    }

    @Override
    public Single<String> getToken() {
        return Single.create(e -> {
            String token = prefs.getString(PREF_API_TOKEN, BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN);
            e.onSuccess(token);
        });
    }

    @Override
    public Completable clearToken() {
        return Completable.create(e -> {
            prefs.edit().putString(PREF_API_TOKEN, null).apply();
            e.onComplete();
        });
    }

}
