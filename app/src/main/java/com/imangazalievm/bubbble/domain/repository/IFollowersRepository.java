package com.imangazalievm.bubbble.domain.repository;


import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface IFollowersRepository {

    Single<List<Follow>> getUserFollowers(UserFollowersRequestParams userFollowersRequestParams);

}
