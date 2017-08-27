package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.filesystem.UrlImageSaver;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesRepository implements IImagesRepository {

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
