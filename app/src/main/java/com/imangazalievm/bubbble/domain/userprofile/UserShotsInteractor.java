package com.imangazalievm.bubbble.domain.userprofile;


import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.global.repositories.ShotsRepository;
import com.imangazalievm.bubbble.domain.global.repositories.UsersRepository;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserShotsInteractor {

    private UsersRepository usersRepository;
    private ShotsRepository shotsRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public UserShotsInteractor(
            UsersRepository usersRepository,
            ShotsRepository shotsRepository,
            SchedulersProvider schedulersProvider
    ) {
        this.usersRepository = usersRepository;
        this.shotsRepository = shotsRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<User> getUser(long userId) {
        return usersRepository.getUser(userId);
    }

    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return shotsRepository.getUserShots(requestParams)
                .subscribeOn(schedulersProvider.io());
    }

}
