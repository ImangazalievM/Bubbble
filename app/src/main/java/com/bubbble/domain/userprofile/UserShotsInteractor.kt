package com.bubbble.domain.userprofile

import com.bubbble.data.shots.ShotsRepository
import com.bubbble.data.users.UsersRepository
import com.bubbble.core.models.Shot
import com.bubbble.core.models.User
import com.bubbble.core.models.UserShotsRequestParams
import javax.inject.Inject

class UserShotsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val shotsRepository: ShotsRepository
) {

    suspend fun getUser(userId: Long): com.bubbble.core.models.User {
        return usersRepository.getUser(userId)
    }

    suspend fun getUserShots(requestParams: com.bubbble.core.models.UserShotsRequestParams): List<com.bubbble.core.models.Shot> {
        return shotsRepository.getUserShots(requestParams)
    }

}