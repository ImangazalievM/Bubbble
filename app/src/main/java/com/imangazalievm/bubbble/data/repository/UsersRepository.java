package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class UsersRepository implements IUsersRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public UsersRepository(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    @Override
    public Single<User> getUser(long userId) {
        return dribbbleApiService.getUser(userId);
    }

}
