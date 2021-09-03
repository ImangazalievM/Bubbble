package com.bubbble.data.shots

import com.bubbble.Constants
import com.bubbble.core.Dribbble
import com.bubbble.core.DribbbleApi
import com.bubbble.data.global.parsing.PageParserManager
import com.bubbble.core.models.Shot
import com.bubbble.core.models.ShotsRequestParams
import com.bubbble.core.models.ShotsSearchParams
import com.bubbble.core.models.UserShotsRequestParams
import javax.inject.Inject

class ShotsRepository @Inject constructor(
    private val dribbbleApi: com.bubbble.core.DribbbleApi,
    private val pageParserManager: PageParserManager,
    private val searchPageParser: SearchPageParser
) {

    suspend fun getShots(requestParams: com.bubbble.core.models.ShotsRequestParams): List<com.bubbble.core.models.Shot> {
        val sort =
            if (requestParams.sort == Constants.SHOTS_SORT_POPULAR)
                com.bubbble.core.Dribbble.Shots.Type.POPULAR
            else com.bubbble.core.Dribbble.Shots.Type.PERSONAL
        return dribbbleApi.getShots(sort.code, requestParams.page, requestParams.pageSize)
    }

    suspend fun getShot(shotId: Long): com.bubbble.core.models.Shot {
        return dribbbleApi.getShot(shotId)
    }

    suspend fun getUserShots(requestParams: com.bubbble.core.models.UserShotsRequestParams): List<com.bubbble.core.models.Shot> {
        return dribbbleApi.getUserShots(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

    suspend fun search(params: com.bubbble.core.models.ShotsSearchParams): List<com.bubbble.core.models.Shot> {
        return pageParserManager.parse(searchPageParser, params)
    }

}