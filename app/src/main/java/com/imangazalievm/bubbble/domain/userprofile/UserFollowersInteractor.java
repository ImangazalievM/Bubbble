package com.imangazalievm.bubbble.domain.userprofile;


import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.global.repository.FollowersRepository;
import com.imangazalievm.bubbble.domain.global.repository.UsersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserFollowersInteractor {

    private UsersRepository usersRepository;
    private FollowersRepository followersRepository;

    @Inject
    public UserFollowersInteractor(UsersRepository usersRepository, FollowersRepository followersRepository) {
        this.usersRepository = usersRepository;
        this.followersRepository = followersRepository;
    }

    public Single<User> getUser(long userId) {
        return usersRepository.getUser(userId);
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return followersRepository.getUserFollowers(requestParams);
    }

}
