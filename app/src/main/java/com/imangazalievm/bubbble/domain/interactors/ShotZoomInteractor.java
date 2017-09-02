package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.repository.ImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ShotZoomInteractor {

    private ImagesRepository imagesRepository;

    @Inject
    public ShotZoomInteractor(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Completable saveImage(String shotImageUrl) {
        return imagesRepository.saveImage(shotImageUrl);
    }

}
