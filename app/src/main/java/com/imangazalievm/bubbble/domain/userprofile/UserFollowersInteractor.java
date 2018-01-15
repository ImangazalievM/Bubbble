package com.imangazalievm.bubbble.domain.userprofile;


import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.global.repositories.FollowersRepository;
import com.imangazalievm.bubbble.presentation.mvp.global.SchedulersProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserFollowersInteractor {

    private FollowersRepository followersRepository;
    private SchedulersProvider schedulersProvider;

    @Inject
    public UserFollowersInteractor(FollowersRepository followersRepository,
                                   SchedulersProvider schedulersProvider) {
        this.followersRepository = followersRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return followersRepository.getUserFollowers(requestParams)
                .subscribeOn(schedulersProvider.io());
    }

}
