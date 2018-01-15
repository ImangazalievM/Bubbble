package com.imangazalievm.bubbble.domain.global.repositories;


import io.reactivex.Completable;

public interface ImagesRepository {

    Completable saveImage(String shotImageUrl);

}
