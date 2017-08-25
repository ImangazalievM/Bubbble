package com.imangazalievm.bubbble.domain.usecases;

import com.imangazalievm.bubbble.di.qualifiers.JobScheduler;
import com.imangazalievm.bubbble.di.qualifiers.UiScheduler;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;
import com.imangazalievm.bubbble.domain.usecases.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetUserUseCase extends SingleUseCase<Long, User> {

    private IUsersRepository usersRepository;

    @Inject
    public GetUserUseCase(@JobScheduler Scheduler jobScheduler,
                          @UiScheduler Scheduler uiScheduler,
                          IUsersRepository usersRepository) {
        super(jobScheduler, uiScheduler);

        this.usersRepository = usersRepository;
    }

    @Override
    protected Single<User> buildSingle(Long userId) {
        return usersRepository.getUser(userId);
    }


}
