package com.bubbble.data.users

import com.bubbble.core.DribbbleApi
import com.bubbble.domain.global.models.Follow
import com.bubbble.domain.global.models.UserFollowersRequestParams
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApi: com.bubbble.core.DribbbleApi
) {

    suspend fun getUserFollowers(requestParams: UserFollowersRequestParams): List<Follow> {
        return dribbbleApi.getUserFollowers(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}