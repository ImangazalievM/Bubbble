package com.imangazalievm.bubbble.domain.shotzoom;


import com.imangazalievm.bubbble.data.shots.ImagesRepository;
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ShotZoomInteractor {

    private ImagesRepository imagesRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotZoomInteractor(
            ImagesRepository imagesRepository,
            SchedulersProvider schedulersProvider
    ) {
        this.imagesRepository = imagesRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Completable saveImage(String shotImageUrl) {
        return imagesRepository.saveImage(shotImageUrl)
                .subscribeOn(schedulersProvider.io());
    }

}
