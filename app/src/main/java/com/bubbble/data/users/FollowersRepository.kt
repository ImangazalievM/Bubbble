package com.bubbble.data.users

import com.bubbble.core.DribbbleApi
import com.bubbble.core.models.Follow
import com.bubbble.core.models.UserFollowersRequestParams
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApi: com.bubbble.core.DribbbleApi
) {

    suspend fun getUserFollowers(requestParams: com.bubbble.core.models.UserFollowersRequestParams): List<com.bubbble.core.models.Follow> {
        return dribbbleApi.getUserFollowers(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}