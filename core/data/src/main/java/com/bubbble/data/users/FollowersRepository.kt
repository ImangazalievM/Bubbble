package com.bubbble.data.users

import com.bubbble.core.models.user.Follow
import com.bubbble.core.models.user.UserFollowersParams
import com.bubbble.core.network.DribbbleApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowersRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi
) {

    suspend fun getUserFollowers(params: UserFollowersParams): List<Follow> {
        TODO()
        //return dribbbleApi.getUserFollowers(
        //    params.userName,
        //    params.page,
        //    params.pageSize
        //)
    }

}