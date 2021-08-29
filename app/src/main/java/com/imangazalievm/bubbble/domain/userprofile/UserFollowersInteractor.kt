package com.imangazalievm.bubbble.domain.userprofile

import com.imangazalievm.bubbble.data.users.FollowersRepository
import com.imangazalievm.bubbble.domain.global.models.Follow
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams
import javax.inject.Inject

class UserFollowersInteractor @Inject constructor(
    private val followersRepository: FollowersRepository
) {

    suspend fun getUserFollowers(requestParams: UserFollowersRequestParams): List<Follow> {
        return followersRepository.getUserFollowers(requestParams)
    }

}