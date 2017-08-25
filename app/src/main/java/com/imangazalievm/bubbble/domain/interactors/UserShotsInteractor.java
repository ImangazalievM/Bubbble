package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.usecases.GetUserShotsUseCase;
import com.imangazalievm.bubbble.domain.usecases.GetUserUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserShotsInteractor {

    private GetUserUseCase getUserUseCase;
    private GetUserShotsUseCase getUserShotsUseCase;

    @Inject
    public UserShotsInteractor(GetUserUseCase getUserUseCase, GetUserShotsUseCase getUserShotsUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.getUserShotsUseCase = getUserShotsUseCase;
    }


    public Single<User> getUser(long userId) {
        return getUserUseCase.getSingle(userId);
    }

    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return getUserShotsUseCase.getSingle(requestParams);
    }

}
