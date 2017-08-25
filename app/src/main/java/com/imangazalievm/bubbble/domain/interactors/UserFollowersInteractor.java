package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Follow;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserFollowersRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.usecases.GetUserFollowersUseCase;
import com.imangazalievm.bubbble.domain.usecases.GetUserShotsUseCase;
import com.imangazalievm.bubbble.domain.usecases.GetUserUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserFollowersInteractor {

    private GetUserUseCase getUserUseCase;
    private GetUserFollowersUseCase getUserFollowersUseCase;

    @Inject
    public UserFollowersInteractor(GetUserUseCase getUserUseCase, GetUserFollowersUseCase getUserFollowersUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.getUserFollowersUseCase = getUserFollowersUseCase;
    }


    public Single<User> getUser(long userId) {
        return getUserUseCase.getSingle(userId);
    }

    public Single<List<Follow>> getUserFollowers(UserFollowersRequestParams requestParams) {
        return getUserFollowersUseCase.getSingle(requestParams);
    }

}
