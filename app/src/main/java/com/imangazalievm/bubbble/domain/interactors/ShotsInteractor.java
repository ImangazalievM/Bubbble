package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsInteractor {

    private ShotsRepository shotsRepository;

    @Inject
    public ShotsInteractor(ShotsRepository shotsRepository) {
        this.shotsRepository = shotsRepository;
    }

    public Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams) {
        return shotsRepository.getShots(shotsRequestParams);
    }

}
