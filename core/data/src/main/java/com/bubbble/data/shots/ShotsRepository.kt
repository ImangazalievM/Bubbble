package com.bubbble.data.shots

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.bubbble.core.models.shot.Shot
import com.bubbble.core.models.feed.ShotsFeedParams
import com.bubbble.core.models.search.SearchParams
import com.bubbble.core.models.shot.ShotDetails
import com.bubbble.core.models.shot.ShotDetailsParams
import com.bubbble.core.models.user.UserShotsParams
import com.bubbble.core.network.DribbbleApi
import com.bubbble.data.global.parsing.PageParserManager
import com.bubbble.data.shots.feed.FeedShotsParser
import com.bubbble.data.global.paging.CommonPagingSource
import com.bubbble.data.shots.details.ShotDetailsParser
import com.bubbble.data.shots.search.SearchPageParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShotsRepository @Inject constructor(
    private val dribbbleApi: DribbbleApi,
    private val pageParserManager: PageParserManager,
    private val feedShotsParser: FeedShotsParser,
    private val searchPageParser: SearchPageParser,
    private val shotDetailsParser: ShotDetailsParser
) {

    fun getShots(
        requestParams: ShotsFeedParams
    ) = Pager(PagingConfig(pageSize = 20)) {
        CommonPagingSource { pagingParams ->
            pageParserManager.parse(
                feedShotsParser,
                requestParams,
                pagingParams
            )
        }
    }.flow

    suspend fun getShot(shotSlug: String): ShotDetails {
        return pageParserManager.parse(shotDetailsParser, ShotDetailsParams(shotSlug))
    }

    suspend fun getUserShots(requestParams: UserShotsParams): List<Shot> {
        TODO()
        //return dribbbleApi.getUserShots(
        //    requestParams.userName,
        //    requestParams.page,
        //    requestParams.pageSize
        //)
    }

    suspend fun search(
        params: SearchParams
    ) = Pager(PagingConfig(pageSize = 20)) {
        CommonPagingSource { pagingParams ->
            pageParserManager.parse(searchPageParser, params, pagingParams)
        }
    }.flow

}