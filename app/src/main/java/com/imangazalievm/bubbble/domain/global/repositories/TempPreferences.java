package com.imangazalievm.bubbble.domain.global.repositories;


import io.reactivex.Completable;
import io.reactivex.Single;

public interface TempPreferences {

    Completable saveToken(String token);
    Single<String> getToken();
    Completable clearToken();

}
