package com.bubbble.domain.userprofile

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.data.users.UsersRepository
import com.bubbble.domain.global.models.Shot
import com.bubbble.domain.global.models.User
import com.bubbble.domain.global.models.UserShotsRequestParams
import javax.inject.Inject

class UserShotsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val shotsRepository: ShotsRepository
) {

    suspend fun getUser(userId: Long): User {
        return usersRepository.getUser(userId)
    }

    suspend fun getUserShots(requestParams: UserShotsRequestParams): List<Shot> {
        return shotsRepository.getUserShots(requestParams)
    }

}