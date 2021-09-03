package com.bubbble.domain.userprofile

import com.bubbble.data.users.UsersRepository
import com.bubbble.models.User
import javax.inject.Inject

class UserProfileInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun getUser(userId: Long): com.bubbble.models.User {
        return usersRepository.getUser(userId)
    }

}