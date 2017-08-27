package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.repository.IImagesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ShotZoomInteractor {

    private IImagesRepository imagesRepository;

    @Inject
    public ShotZoomInteractor(IImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Completable saveImage(String shotImageUrl) {
        return imagesRepository.saveImage(shotImageUrl);
    }

}
