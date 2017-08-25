package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.usecases.GetShotCommentsUseCase;
import com.imangazalievm.bubbble.domain.usecases.GetShotUseCase;
import com.imangazalievm.bubbble.domain.usecases.SaveShotImageUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ShotDetailsInteractor {

    private GetShotUseCase getShotUseCase;
    private GetShotCommentsUseCase getShotCommentsUseCase;
    private SaveShotImageUseCase saveShotImageUseCase;

    @Inject
    public ShotDetailsInteractor(GetShotUseCase getShotUseCase,
                                 GetShotCommentsUseCase getShotCommentsUseCase,
                                 SaveShotImageUseCase saveShotImageUseCase) {
        this.getShotUseCase = getShotUseCase;
        this.getShotCommentsUseCase = getShotCommentsUseCase;
        this.saveShotImageUseCase = saveShotImageUseCase;
    }


    public Single<Shot> getShot(long shotId) {
        return getShotUseCase.getSingle(shotId);
    }

    public Single<List<Comment>> getShotComments(ShotCommentsRequestParams shotCommentsRequestParams) {
        return getShotCommentsUseCase.getSingle(shotCommentsRequestParams);
    }

    public Completable saveImage(String imageUrl) {
        return saveShotImageUseCase.getCompletable(imageUrl);
    }

}
