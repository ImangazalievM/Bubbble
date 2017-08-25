package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetTempShotUseCase extends SingleUseCase<Long, Shot> {

    private IShotsRepository shotsRepository;

    @Inject
    public GetTempShotUseCase(@JobScheduler Scheduler jobScheduler,
                              @UiScheduler Scheduler uiScheduler,
                              IShotsRepository shotsRepository) {
        super(jobScheduler, uiScheduler);

        this.shotsRepository = shotsRepository;
    }

    @Override
    protected Single<Shot> buildSingle(Long shotId) {
        return shotsRepository.getShot(shotId);
    }


}
