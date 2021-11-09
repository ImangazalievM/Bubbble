package com.bubbble.data.shots

import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.models.search.SearchParams
import com.bubbble.core.models.user.UserShotsParams
import com.bubbble.core.network.DribbbleApi
import com.bubbble.data.global.parsing.PageParserManager
import com.bubbble.data.shots.feed.FeedShotsParser
import com.bubbble.data.shots.search.SearchPageParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShotsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi,
    private val pageParserManager: PageParserManager,
    private val searchPageParser: SearchPageParser,
    private val feedShotsParser: FeedShotsParser
) {

    suspend fun getShots(
        requestParams: ShotsFeedParams
    ): List<Shot> = withContext(Dispatchers.IO) {
        pageParserManager.parse(feedShotsParser, requestParams)
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

    suspend fun search(
        params: SearchParams
    ): List<Shot> = withContext(Dispatchers.IO) {
        pageParserManager.parse(searchPageParser, params)
    }

}