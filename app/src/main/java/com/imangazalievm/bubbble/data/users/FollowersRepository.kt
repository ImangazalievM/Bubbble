package com.imangazalievm.bubbble.data.users

import com.imangazalievm.bubbble.data.global.network.DribbbleApi
import com.imangazalievm.bubbble.domain.global.models.Follow
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUserFollowers(requestParams: UserFollowersRequestParams): List<Follow> {
        return dribbbleApi.getUserFollowers(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}