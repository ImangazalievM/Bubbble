package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.global.repository.FollowersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FollowersRepositoryImpl implements FollowersRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public FollowersRepositoryImpl(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return dribbbleApiService.getUserFollowers(requestParams.getUserId(), requestParams.getPage(), requestParams.getPageSize());
    }

}
