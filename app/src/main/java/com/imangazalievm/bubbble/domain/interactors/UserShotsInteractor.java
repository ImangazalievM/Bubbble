package com.imangazalievm.bubbble.domain.interactors;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserShotsInteractor {

    private IUsersRepository usersRepository;
    private IShotsRepository shotsRepository;

    @Inject
    public UserShotsInteractor(IUsersRepository usersRepository, IShotsRepository shotsRepository) {
        this.usersRepository = usersRepository;
        this.shotsRepository = shotsRepository;
    }

    public Single<User> getUser(long userId) {
        return usersRepository.getUser(userId);
    }

    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return shotsRepository.getUserShots(requestParams);
    }

}
