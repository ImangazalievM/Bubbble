package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserFollowersInteractor {

    private IUsersRepository usersRepository;
    private IFollowersRepository followersRepository;

    @Inject
    public UserFollowersInteractor(IUsersRepository usersRepository, IFollowersRepository followersRepository) {
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
