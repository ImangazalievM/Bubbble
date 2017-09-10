package com.imangazalievm.bubbble.domain.repository;


import com.imangazalievm.bubbble.domain.models.User;

import io.reactivex.Single;

public interface UsersRepository {

    Single<User> getUser(long userId);

}
