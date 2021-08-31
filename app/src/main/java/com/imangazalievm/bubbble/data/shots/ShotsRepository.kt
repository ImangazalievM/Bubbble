package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.data.global.network.Dribbble
import com.imangazalievm.bubbble.data.global.network.DribbbleApi
import com.imangazalievm.bubbble.data.global.parsing.PageParserManager
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchParams
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
import javax.inject.Inject

class ShotsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi,
    private val pageParserManager: PageParserManager,
    private val searchPageParser: SearchPageParser
) {

    suspend fun getShots(requestParams: ShotsRequestParams): List<Shot> {
        val sort =
            if (requestParams.sort == Constants.SHOTS_SORT_POPULAR)
                Dribbble.Shots.Type.POPULAR
            else Dribbble.Shots.Type.PERSONAL
        return dribbbleApi.getShots(sort.code, requestParams.page, requestParams.pageSize)
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

    suspend fun search(params: ShotsSearchParams): List<Shot> {
        return pageParserManager.parse(searchPageParser, params)
    }

}