package com.imangazalievm.bubbble.data.users;

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.global.repositories.UsersRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class UsersRepositoryImpl implements UsersRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public UsersRepositoryImpl(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    @Override
    public Single<User> getUser(long userId) {
        return dribbbleApiService.getUser(userId);
    }

}
