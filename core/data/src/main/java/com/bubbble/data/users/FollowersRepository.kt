package com.bubbble.data.users

import com.bubbble.core.models.user.Follow
import com.bubbble.core.models.user.UserFollowersParams
import com.bubbble.core.network.DribbbleApi
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUserFollowers(params: UserFollowersParams): List<Follow> {
        return dribbbleApi.getUserFollowers(
            params.userId,
            params.page,
            params.pageSize
        )
    }

}