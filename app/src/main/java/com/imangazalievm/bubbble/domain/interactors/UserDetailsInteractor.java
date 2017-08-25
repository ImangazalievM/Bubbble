package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.usecases.GetUserUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserDetailsInteractor {

    private GetUserUseCase getUserUseCase;

    @Inject
    public UserDetailsInteractor(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }


    public Single<User> getUser(long userId) {
        return getUserUseCase.getSingle(userId);
    }

}
