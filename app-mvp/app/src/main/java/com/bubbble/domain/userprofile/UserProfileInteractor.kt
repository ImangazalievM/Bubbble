package com.bubbble.domain.userprofile

import com.bubbble.data.users.UsersRepository
import com.bubbble.core.models.user.User
import javax.inject.Inject

class UserProfileInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun getUser(userName: String): User {
        return usersRepository.getUser(userName)
    }

}