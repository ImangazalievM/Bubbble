package com.imangazalievm.bubbble.domain.global.repositories;


import com.imangazalievm.bubbble.domain.global.models.Follow;
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface FollowersRepository {

    Single<List<Follow>> getUserFollowers(UserFollowersRequestParams userFollowersRequestParams);

}
