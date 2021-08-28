package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.Constants
import com.imangazalievm.bubbble.data.global.network.DribbbleApiService
import com.imangazalievm.bubbble.data.shots.DribbbleSearchDataSource
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams
import com.imangazalievm.bubbble.domain.global.models.Shot
import com.imangazalievm.bubbble.data.global.network.Dribbble
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams
import io.reactivex.Single
import javax.inject.Inject

class ShotsRepository @Inject constructor(
    private val dribbbleApiService: DribbbleApiService,
    private val dribbbleSearchDataSource: DribbbleSearchDataSource
) {

    fun getShots(requestParams: ShotsRequestParams): Single<List<Shot>> {
        val sort =
            if (requestParams.sort == Constants.SHOTS_SORT_POPULAR) Dribbble.Shots.type_popular else Dribbble.Shots.type_recent
        return dribbbleApiService.getShots(sort, requestParams.page, requestParams.pageSize)
    }

    fun getShot(shotId: Long): Single<Shot> {
        return dribbbleApiService.getShot(shotId)
    }

    fun getUserShots(requestParams: UserShotsRequestParams): Single<List<Shot>> {
        return dribbbleApiService.getUserShots(
            requestParams.userId,
            requestParams.page,
            requestParams.pageSize
        )
    }

    fun search(requestParams: ShotsSearchRequestParams): Single<List<Shot>> {
        return dribbbleSearchDataSource.search(
            requestParams.searchQuery,
            requestParams.sort,
            requestParams.page,
            requestParams.pageSize
        )
    }

}