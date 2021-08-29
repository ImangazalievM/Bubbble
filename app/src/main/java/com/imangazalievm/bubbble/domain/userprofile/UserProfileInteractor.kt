package com.imangazalievm.bubbble.domain.userprofile

import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class UserProfileInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun getUser(userId: Long): User {
        return usersRepository.getUser(userId)
    }

}