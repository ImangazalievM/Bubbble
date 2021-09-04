package com.bubbble.domain.userprofile

import com.bubbble.core.models.user.Follow
import com.bubbble.data.users.FollowersRepository
import com.bubbble.core.models.user.UserFollowersParams
import javax.inject.Inject

class UserFollowersInteractor @Inject constructor(
    private val followersRepository: FollowersRepository
) {

    suspend fun getUserFollowers(params: UserFollowersParams): List<Follow> {
        return followersRepository.getUserFollowers(params)
    }

}