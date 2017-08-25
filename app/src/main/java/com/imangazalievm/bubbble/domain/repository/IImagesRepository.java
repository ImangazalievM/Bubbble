package com.imangazalievm.bubbble.domain.repository;


import io.reactivex.Completable;

public interface IImagesRepository {

    Completable saveImage(String shotImageUrl);

}
