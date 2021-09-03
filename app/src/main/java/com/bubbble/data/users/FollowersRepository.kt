package com.bubbble.data.users

import com.bubbble.core.DribbbleApi
import com.bubbble.models.Follow
import com.bubbble.models.UserFollowersRequestParams
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApi: com.bubbble.core.DribbbleApi
) {

    suspend fun getUserFollowers(requestParams: com.bubbble.models.UserFollowersRequestParams): List<com.bubbble.models.Follow> {
        return dribbbleApi.getUserFollowers(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}