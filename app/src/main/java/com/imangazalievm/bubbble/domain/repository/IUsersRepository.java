package com.imangazalievm.bubbble.domain.repository;


import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface IUsersRepository {

    Single<User> getUser(long userId);

}
