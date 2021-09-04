package com.bubbble.data.shots

import com.bubbble.Constants
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.shot.ShotsParams
import com.bubbble.core.models.shot.ShotsSearchParams
import com.bubbble.core.models.user.UserShotsParams
import com.bubbble.core.network.Dribbble
import com.bubbble.core.network.DribbbleApi
import com.bubbble.data.global.parsing.PageParserManager
import javax.inject.Inject

class ShotsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi,
    private val pageParserManager: PageParserManager,
    private val searchPageParser: SearchPageParser
) {

    suspend fun getShots(requestParams: ShotsParams): List<Shot> {
        val sort =
            if (requestParams.sort == Constants.SHOTS_SORT_POPULAR)
                Dribbble.Shots.Type.POPULAR
            else Dribbble.Shots.Type.PERSONAL
        return dribbbleApi.getShots(sort.code, requestParams.page, requestParams.pageSize)
    }

    suspend fun getShot(shotId: Long): Shot {
        return dribbbleApi.getShot(shotId)
    }

    suspend fun getUserShots(requestParams: UserShotsParams): List<Shot> {
        return dribbbleApi.getUserShots(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

    suspend fun search(params: ShotsSearchParams): List<Shot> {
        return pageParserManager.parse(searchPageParser, params)
    }

}