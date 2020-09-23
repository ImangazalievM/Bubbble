package com.imangazalievm.bubbble.domain.shotslist;


import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.global.repositories.ShotsRepository;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsInteractor {

    private ShotsRepository shotsRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotsInteractor(
            ShotsRepository shotsRepository,
            SchedulersProvider schedulersProvider
    ) {
        this.shotsRepository = shotsRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams) {
        return shotsRepository.getShots(shotsRequestParams)
                .subscribeOn(schedulersProvider.io());
    }

}
