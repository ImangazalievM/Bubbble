package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.datastores.FollowersDataStore;
import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FollowersRepository implements IFollowersRepository {

    private FollowersDataStore followersDataStore;

    @Inject
    public FollowersRepository(FollowersDataStore followersDataStore) {
        this.followersDataStore = followersDataStore;
    }

    @Override
    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams userFollowersRequestParams) {
        return followersDataStore.getUserFollowers(userFollowersRequestParams);
    }

}
