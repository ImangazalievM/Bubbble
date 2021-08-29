package com.imangazalievm.bubbble.domain.userprofile

import com.imangazalievm.bubbble.data.shots.ShotsRepository
import com.imangazalievm.bubbble.data.users.UsersRepository
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.User
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
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