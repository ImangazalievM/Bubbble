package com.imangazalievm.bubbble.data.users

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService
import com.imangazalievm.bubbble.domain.global.models.UserFollowersRequestParams
import com.imangazalievm.bubbble.domain.global.models.Follow
import io.reactivex.Single
import javax.inject.Inject

class FollowersRepository @Inject constructor(
    private val dribbbleApiService: DribbbleApiService
    ) {

    fun getUserFollowers(requestParams: UserFollowersRequestParams): Single<List<Follow>> {
        return dribbbleApiService.getUserFollowers(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}