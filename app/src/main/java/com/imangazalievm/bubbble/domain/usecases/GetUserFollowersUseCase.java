package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetUserFollowersUseCase extends SingleUseCase<UserFollowersRequestParams, List<Follow>> {

    private IFollowersRepository followersRepository;

    @Inject
    public GetUserFollowersUseCase(@JobScheduler Scheduler jobScheduler,
                                   @UiScheduler Scheduler uiScheduler,
                                   IFollowersRepository followersRepository) {
        super(jobScheduler, uiScheduler);

        this.followersRepository = followersRepository;
    }

    @Override
    protected Single<List<Follow>> buildSingle(UserFollowersRequestParams requestParams) {
        return followersRepository.getUserFollowers(requestParams);
    }


}
