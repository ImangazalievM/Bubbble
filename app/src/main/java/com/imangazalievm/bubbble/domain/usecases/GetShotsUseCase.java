package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetShotsUseCase extends SingleUseCase<ShotsRequestParams, List<Shot>> {

    private IShotsRepository shotsRepository;

    @Inject
    public GetShotsUseCase(@JobScheduler Scheduler jobScheduler,
                           @UiScheduler Scheduler uiScheduler,
                           IShotsRepository shotsRepository) {
        super(jobScheduler, uiScheduler);

        this.shotsRepository = shotsRepository;
    }

    @Override
    protected Single<List<Shot>> buildSingle(ShotsRequestParams requestParams) {
        return shotsRepository.getShot(requestParams);
    }


}
