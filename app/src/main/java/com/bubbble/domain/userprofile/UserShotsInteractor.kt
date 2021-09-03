package com.bubbble.domain.userprofile

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.data.users.UsersRepository
import com.bubbble.models.Shot
import com.bubbble.models.User
import com.bubbble.models.UserShotsRequestParams
import javax.inject.Inject

class UserShotsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val shotsRepository: ShotsRepository
) {

    suspend fun getUser(userId: Long): com.bubbble.models.User {
        return usersRepository.getUser(userId)
    }

    suspend fun getUserShots(requestParams: com.bubbble.models.UserShotsRequestParams): List<com.bubbble.models.Shot> {
        return shotsRepository.getUserShots(requestParams)
    }

}