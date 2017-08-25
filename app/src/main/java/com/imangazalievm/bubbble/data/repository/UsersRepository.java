package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.datastores.UsersDataStore;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class UsersRepository implements IUsersRepository {

    private UsersDataStore usersDataStore;

    @Inject
    public UsersRepository(UsersDataStore usersDataStore) {
        this.usersDataStore = usersDataStore;
    }


    @Override
    public Single<User> getUser(long userId) {
        return usersDataStore.getUser(userId);
    }

}
