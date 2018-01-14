package com.imangazalievm.bubbble.domain.shotssearch;


import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.global.repository.ShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsSearchInteractor {

    private ShotsRepository shotsRepository;

    @Inject
    public ShotsSearchInteractor(ShotsRepository shotsRepository) {
        this.shotsRepository = shotsRepository;
    }

    public Single<List<Shot>> search(ShotsSearchRequestParams requestParams) {
        return shotsRepository.search(requestParams);
    }

}
