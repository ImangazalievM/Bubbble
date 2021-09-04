package com.bubbble.domain.userprofile

import com.bubbble.data.users.UsersRepository
import com.bubbble.core.models.user.User
import javax.inject.Inject

class UserDetailsInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun getUser(userId: Long): User {
        return usersRepository.getUser(userId)
    }

}