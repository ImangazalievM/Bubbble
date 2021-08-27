package com.imangazalievm.bubbble.data.shots;

import com.imangazalievm.bubbble.data.global.filesystem.UrlImageSaver;
import com.imangazalievm.bubbble.domain.global.repositories.ImagesRepository;

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
