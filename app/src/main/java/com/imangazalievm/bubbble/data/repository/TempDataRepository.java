package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.preferences.TempPreferences;
import com.imangazalievm.bubbble.domain.repository.ITempDataRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TempDataRepository implements ITempDataRepository {

    private TempPreferences tempPreferences;

    @Inject
    public TempDataRepository(TempPreferences tempPreferences) {
        this.tempPreferences = tempPreferences;
    }


    @Override
    public Completable saveToken(String token) {
        return null;
    }

    @Override
    public Single<String> getToken() {
        return null;
    }

    @Override
    public Completable clearToken() {
        return null;
    }

}
