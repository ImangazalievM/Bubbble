package com.imangazalievm.bubbble.data.users;

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.global.models.User;

import javax.inject.Inject;

import io.reactivex.Single;

public class UsersRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public UsersRepository(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    public Single<User> getUser(long userId) {
        return dribbbleApiService.getUser(userId);
    }

}
