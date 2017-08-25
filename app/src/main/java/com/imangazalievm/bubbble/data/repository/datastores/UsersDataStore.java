package com.imangazalievm.bubbble.data.repository.datastores;

import com.imangazalievm.bubbble.data.mappers.UserResponseMapper;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.User;

import javax.inject.Inject;

import io.reactivex.Single;

public class UsersDataStore {

    private DribbbleApiService dribbbleApiService;
    private UserResponseMapper userResponseMapper;

    @Inject
    public UsersDataStore(DribbbleApiService dribbbleApiService, UserResponseMapper userResponseMapper) {
        this.dribbbleApiService = dribbbleApiService;
        this.userResponseMapper = userResponseMapper;
    }

    public Single<User> getUser(long userId) {
        return dribbbleApiService.getUser(userId)
                .map(user -> userResponseMapper.map(user));
    }

}
