package com.imangazalievm.bubbble.domain.userprofile

import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.domain.global.models.User
import javax.inject.Inject

class UserDetailsInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun getUser(userId: Long): User {
        return usersRepository.getUser(userId)
    }

}