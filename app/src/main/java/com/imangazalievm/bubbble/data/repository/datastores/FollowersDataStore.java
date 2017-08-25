package com.imangazalievm.bubbble.data.repository.datastores;

import com.imangazalievm.bubbble.data.mappers.FollowResponseMapper;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FollowersDataStore {

    private DribbbleApiService dribbbleApiService;
    private FollowResponseMapper followResponseMapper;

    @Inject
    public FollowersDataStore(DribbbleApiService dribbbleApiService, FollowResponseMapper followResponseMapper) {
        this.dribbbleApiService = dribbbleApiService;
        this.followResponseMapper = followResponseMapper;
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return dribbbleApiService.getUserFollowers(requestParams.getUserId(), requestParams.getPage(), requestParams.getPageSize())
                .map(user -> followResponseMapper.map(user));
    }

}
