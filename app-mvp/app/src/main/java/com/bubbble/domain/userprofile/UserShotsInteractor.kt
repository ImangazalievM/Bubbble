package com.bubbble.domain.userprofile

import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.user.User
import com.bubbble.core.models.user.UserShotsParams
import com.bubbble.data.shots.ShotsRepository
import com.bubbble.data.users.UsersRepository
import javax.inject.Inject

class UserShotsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val shotsRepository: ShotsRepository
) {

    suspend fun getUser(userId: Long): User {
        return usersRepository.getUser(userId)
    }

    suspend fun getUserShots(params: UserShotsParams): List<Shot> {
        return shotsRepository.getUserShots(params)
    }

}