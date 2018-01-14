package com.imangazalievm.bubbble.domain.global.repository;


import io.reactivex.Completable;
import io.reactivex.Single;

public interface TempDataRepository {

    Completable saveToken(String token);
    Single<String> getToken();
    Completable clearToken();

}
