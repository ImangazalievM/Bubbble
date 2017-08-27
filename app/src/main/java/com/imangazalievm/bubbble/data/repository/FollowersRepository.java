package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FollowersRepository implements IFollowersRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public FollowersRepository(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return dribbbleApiService.getUserFollowers(requestParams.getUserId(),
                requestParams.getPage(), requestParams.getPageSize());
    }

}
