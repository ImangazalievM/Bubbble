package com.imangazalievm.bubbble.domain.userprofile;


import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.global.repository.UsersRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor {

    private UsersRepository usersRepository;

    @Inject
    public UserProfileInteractor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Single<User> getUser(long userId) {
        return usersRepository.getUser(userId);
    }

}
