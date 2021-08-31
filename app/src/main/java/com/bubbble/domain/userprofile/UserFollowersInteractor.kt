package com.bubbble.domain.userprofile

import com.bubbble.data.users.FollowersRepository
import com.bubbble.domain.global.models.Follow
import com.bubbble.domain.global.models.UserFollowersRequestParams
import javax.inject.Inject

class UserFollowersInteractor @Inject constructor(
    private val followersRepository: FollowersRepository
) {

    suspend fun getUserFollowers(requestParams: UserFollowersRequestParams): List<Follow> {
        return followersRepository.getUserFollowers(requestParams)
    }

}