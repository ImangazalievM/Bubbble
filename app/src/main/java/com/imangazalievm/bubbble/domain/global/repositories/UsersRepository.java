package com.imangazalievm.bubbble.domain.global.repositories;


import com.imangazalievm.bubbble.domain.global.models.User;

import io.reactivex.Single;

public interface UsersRepository {

    Single<User> getUser(long userId);

}
