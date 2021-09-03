package com.bubbble.domain.userprofile

import com.bubbble.data.users.FollowersRepository
import com.bubbble.core.models.Follow
import com.bubbble.core.models.UserFollowersRequestParams
import javax.inject.Inject

class UserFollowersInteractor @Inject constructor(
    private val followersRepository: FollowersRepository
) {

    suspend fun getUserFollowers(requestParams: com.bubbble.core.models.UserFollowersRequestParams): List<com.bubbble.core.models.Follow> {
        return followersRepository.getUserFollowers(requestParams)
    }

}