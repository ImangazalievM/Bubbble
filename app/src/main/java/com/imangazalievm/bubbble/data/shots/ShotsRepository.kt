package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.data.global.network.Dribbble
import com.imangazalievm.bubbble.data.global.network.DribbbleApi
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
import javax.inject.Inject

class ShotsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi,
    private val dribbbleSearchDataSource: DribbbleSearchDataSource
) {

    suspend fun getShots(requestParams: ShotsRequestParams): List<Shot> {
        val sort =
            if (requestParams.sort == Constants.SHOTS_SORT_POPULAR) Dribbble.Shots.type_popular else Dribbble.Shots.type_recent
        return dribbbleApi.getShots(sort, requestParams.page, requestParams.pageSize)
    }

    suspend fun getShot(shotId: Long): Shot {
        return dribbbleApi.getShot(shotId)
    }

    suspend fun getUserShots(requestParams: UserShotsRequestParams): List<Shot> {
        return dribbbleApi.getUserShots(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

    suspend fun search(requestParams: ShotsSearchRequestParams): List<Shot> {
        return dribbbleSearchDataSource.search(
            requestParams.searchQuery,
            requestParams.sort,
            requestParams.page,
            requestParams.pageSize
        )
    }

}