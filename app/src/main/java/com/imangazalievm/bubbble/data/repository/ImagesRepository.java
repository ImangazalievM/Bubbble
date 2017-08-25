package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.datastores.ImagesDataStore;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ImagesRepository implements IImagesRepository {

    private ImagesDataStore imagesDataStore;

    @Inject
    public ImagesRepository(ImagesDataStore imagesDataStore) {
        this.imagesDataStore = imagesDataStore;
    }

    @Override
    public Completable saveImage(String shotImageUrl) {
        return imagesDataStore.saveImage(shotImageUrl);
    }

}
