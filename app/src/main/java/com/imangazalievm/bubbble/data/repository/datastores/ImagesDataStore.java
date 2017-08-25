package com.imangazalievm.bubbble.data.repository.datastores;

import com.imangazalievm.bubbble.data.filesystem.UrlImageSaver;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesDataStore {

    private UrlImageSaver urlImageSaver;

    @Inject
    public ImagesDataStore(UrlImageSaver urlImageSaver) {
        this.urlImageSaver = urlImageSaver;
    }

    public Completable saveImage(String shotImageUrl) {
        return Completable.create(e -> {
            urlImageSaver.saveImage(shotImageUrl);
            e.onComplete();
        });
    }

}
