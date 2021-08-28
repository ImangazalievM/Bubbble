package com.imangazalievm.bubbble.data.shots;

import com.imangazalievm.bubbble.data.global.filesystem.UrlImageSaver;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesRepository {

    private UrlImageSaver urlImageSaver;

    @Inject
    public ImagesRepository(UrlImageSaver urlImageSaver) {
        this.urlImageSaver = urlImageSaver;
    }

    public Completable saveImage(String shotImageUrl) {
        return Completable.create(e -> {
            urlImageSaver.saveImage(shotImageUrl);
            e.onComplete();
        });
    }

}
