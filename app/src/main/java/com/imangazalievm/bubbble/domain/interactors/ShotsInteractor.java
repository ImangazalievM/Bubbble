package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsInteractor {

    private IShotsRepository shotsRepository;

    @Inject
    public ShotsInteractor(IShotsRepository shotsRepository) {
        this.shotsRepository = shotsRepository;
    }

    public Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams) {
        return shotsRepository.getShots(shotsRequestParams);
    }

}
