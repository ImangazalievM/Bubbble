package com.imangazalievm.bubbble.domain.global.repository;


import com.imangazalievm.bubbble.domain.global.models.User;

import io.reactivex.Single;

public interface UsersRepository {

    Single<User> getUser(long userId);

}
