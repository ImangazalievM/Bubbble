package com.imangazalievm.bubbble.domain.interactors;


import android.util.Log;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;

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
