package com.imangazalievm.bubbble.data.shots

import com.imangazalievm.bubbble.data.global.network.DribbbleApiService
import com.imangazalievm.bubbble.domain.global.models.Comment
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams
import io.reactivex.Single
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val dribbbleApiService: DribbbleApiService) {

    fun getComments(requestParams: ShotCommentsRequestParams): Single<List<Comment>> {
        return dribbbleApiService.getShotComments(
            requestParams.shotId,
            requestParams.page,
            requestParams.pageSize
        )
    }

}