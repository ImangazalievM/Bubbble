package com.imangazalievm.bubbble.domain.usecases;

import android.util.Log;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetUserShotsUseCase extends SingleUseCase<UserShotsRequestParams, List<Shot>> {

    private IShotsRepository shotsRepository;

    @Inject
    public GetUserShotsUseCase(@JobScheduler Scheduler jobScheduler,
                               @UiScheduler Scheduler uiScheduler,
                               IShotsRepository shotsRepository) {
        super(jobScheduler, uiScheduler);

        this.shotsRepository = shotsRepository;
    }

    @Override
    protected Single<List<Shot>> buildSingle(UserShotsRequestParams userShotsRequestParams) {
        return shotsRepository.getUserShots(userShotsRequestParams);
    }


}
