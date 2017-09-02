package com.imangazalievm.bubbble.domain.repository;


import io.reactivex.Completable;

public interface ImagesRepository {

    Completable saveImage(String shotImageUrl);

}
