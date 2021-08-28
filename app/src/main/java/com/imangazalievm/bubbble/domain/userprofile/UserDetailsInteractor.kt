package com.imangazalievm.bubbble.domain.userprofile

import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class UserDetailsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulersProvider: SchedulersProvider
) {

    fun getUser(userId: Long): Single<User> {
        return usersRepository.getUser(userId)
            .subscribeOn(schedulersProvider.io())
    }

}