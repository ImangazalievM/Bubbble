package com.imangazalievm.bubbble.domain.shotssearch;


import com.imangazalievm.bubbble.data.shots.ShotsRepository;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsSearchInteractor {

    private ShotsRepository shotsRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public ShotsSearchInteractor(
            ShotsRepository shotsRepository,
            SchedulersProvider schedulersProvider
    ) {
        this.shotsRepository = shotsRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Shot>> search(ShotsSearchRequestParams requestParams) {
        return shotsRepository.search(requestParams)
                .subscribeOn(schedulersProvider.io());
    }

}
