package com.bubbble.domain.userprofile

import com.bubbble.data.users.FollowersRepository
import com.bubbble.models.Follow
import com.bubbble.models.UserFollowersRequestParams
import javax.inject.Inject

class UserFollowersInteractor @Inject constructor(
    private val followersRepository: FollowersRepository
) {

    suspend fun getUserFollowers(requestParams: com.bubbble.models.UserFollowersRequestParams): List<com.bubbble.models.Follow> {
        return followersRepository.getUserFollowers(requestParams)
    }

}