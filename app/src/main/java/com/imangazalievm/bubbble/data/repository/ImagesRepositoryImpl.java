package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.filesystem.UrlImageSaver;
import com.imangazalievm.bubbble.domain.repository.ImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesRepositoryImpl implements ImagesRepository {

    private UrlImageSaver urlImageSaver;

    @Inject
    public ImagesRepositoryImpl(UrlImageSaver urlImageSaver) {
        this.urlImageSaver = urlImageSaver;
    }

    public Completable saveImage(String shotImageUrl) {
        return Completable.create(e -> {
            urlImageSaver.saveImage(shotImageUrl);
            e.onComplete();
        });
    }

}
