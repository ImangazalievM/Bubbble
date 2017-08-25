package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.usecases.GetShotsUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsInteractor {

    private GetShotsUseCase getShotsUseCase;

    @Inject
    public ShotsInteractor(GetShotsUseCase getShotsUseCase) {
        this.getShotsUseCase = getShotsUseCase;
    }


    public Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams) {
        return getShotsUseCase.getSingle(shotsRequestParams);
    }

}
