package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.usecases.SaveShotImageUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

public class ShotZoomInteractor {

    private SaveShotImageUseCase saveShotImageUseCase;

    @Inject
    public ShotZoomInteractor(SaveShotImageUseCase saveShotImageUseCase) {
        this.saveShotImageUseCase = saveShotImageUseCase;
    }


    public Completable saveImage(String shotImageUrl) {
        return saveShotImageUseCase.getCompletable(shotImageUrl);
    }

}
