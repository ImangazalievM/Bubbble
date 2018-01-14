package com.imangazalievm.bubbble.domain.userprofile;


import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.global.repository.UsersRepository;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor {

    private UsersRepository usersRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public UserProfileInteractor(UsersRepository usersRepository,
                                 SchedulersProvider schedulersProvider) {
        this.usersRepository = usersRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<User> getUser(long userId) {
        return usersRepository.getUser(userId)
                .subscribeOn(schedulersProvider.io());
    }

}
