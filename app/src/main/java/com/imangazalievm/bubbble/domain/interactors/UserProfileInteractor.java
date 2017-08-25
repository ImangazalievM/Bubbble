package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.usecases.GetUserShotsUseCase;
import com.imangazalievm.bubbble.domain.usecases.GetUserUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileInteractor {

    private GetUserUseCase getUserUseCase;
    private GetUserShotsUseCase getUserShotsUseCase;

    @Inject
    public UserProfileInteractor(GetUserUseCase getUserUseCase, GetUserShotsUseCase getUserShotsUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.getUserShotsUseCase = getUserShotsUseCase;
    }


    public Single<User> getUser(long userId) {
        return getUserUseCase.getSingle(userId);
    }

}
