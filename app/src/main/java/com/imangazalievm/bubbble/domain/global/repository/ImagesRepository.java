package com.imangazalievm.bubbble.domain.global.repository;


import io.reactivex.Completable;

public interface ImagesRepository {

    Completable saveImage(String shotImageUrl);

}
